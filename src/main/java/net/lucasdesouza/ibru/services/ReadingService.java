package net.lucasdesouza.ibru.services;

import net.lucasdesouza.ibru.models.Brew;
import net.lucasdesouza.ibru.models.Reading;
import net.lucasdesouza.ibru.repositories.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingService {

    private final ReadingRepository readingRepository;

    @Autowired
    ReadingService(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    public List<Reading> getAllReadingsByBrew(Brew brew) {
        return readingRepository.findAllByBrew(brew);
    }

    public Optional<Reading> getReadingById(String id) {
        return readingRepository.findById(id);
    }

    public Reading createReading(Brew brew, Double gravity) {
        return createReading(brew, gravity, new Date(), false, false);
    }

    public Reading createReading(Brew brew, Double gravity, Date date, boolean starting, boolean ending) {
        Reading reading = new Reading();
        reading.setGravity(gravity);
        reading.setDate(date);
        reading.setBrew(brew);
        reading.setStart(starting);
        return readingRepository.save(reading);
    }

    public void deleteReadingById(String id) throws Exception {
        Optional<Reading> reading = getReadingById(id);
        if(reading.isPresent()) {
            if(!reading.get().getStart() && !reading.get().getEnd()) {
                readingRepository.deleteById(id);
            } else {
                throw new Exception("Can't delete starting or ending readings");
            }
        }
    }
}
