package net.lucasdesouza.ibru.services;

import net.lucasdesouza.ibru.models.Brew;
import net.lucasdesouza.ibru.models.CreateBrewRequest;
import net.lucasdesouza.ibru.models.Reading;
import net.lucasdesouza.ibru.repositories.BrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrewService {
    private final BrewRepository brewRepository;
    private final ReadingService readingService;

    @Autowired
    BrewService(BrewRepository brewRepository, ReadingService readingService){
        this.brewRepository = brewRepository;
        this.readingService = readingService;
    }

    public List<Brew> getAllBrews() {
        return brewRepository.findAll();
    }

    public Brew createNewBrew(CreateBrewRequest request) {
        Date date = new Date();

        Brew brew = new Brew();
        brew.setName(request.getName());
        brew.setDetails(request.getDetails());
        brew.setBottleDate(null);
        brew.setStartDate(date);

        brewRepository.save(brew);
        brew.setReadings(new ArrayList<>());
        Reading reading = readingService.createReading(brew, request.getStartingGravity(), date, true, false);
        brew.getReadings().add(reading);

        return brew;
    }

    public Optional<Brew> getBrewById(String id) {
        return brewRepository.findById(id);
    }

    public void deleteBrewById(String id) {
        brewRepository.deleteById(id);
    }
}
