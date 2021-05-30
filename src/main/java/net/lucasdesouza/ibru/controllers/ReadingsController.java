package net.lucasdesouza.ibru.controllers;

import net.lucasdesouza.ibru.models.Brew;
import net.lucasdesouza.ibru.models.CreateReadingRequest;
import net.lucasdesouza.ibru.models.Reading;
import net.lucasdesouza.ibru.services.BrewService;
import net.lucasdesouza.ibru.services.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/readings")
public class ReadingsController {

    private final BrewService brewService;
    private final ReadingService readingService;

    @Autowired
    ReadingsController(BrewService brewService, ReadingService readingService) {
        this.brewService = brewService;
        this.readingService = readingService;
    }

    @GetMapping
    public ResponseEntity<List<Reading>> getAllReadings(@RequestParam String brewId) {
        Optional<Brew> brew = brewService.getBrewById(brewId);
        if (brew.isPresent()) {
            List<Reading> readings = readingService.getAllReadingsByBrew(brew.get());
            return new ResponseEntity<>(readings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Reading> createNewReading(@RequestBody CreateReadingRequest request) {
        Optional<Brew> brew = brewService.getBrewById(request.getBrewId());
        if (brew.isPresent()) {
            Reading reading = readingService.createReading(brew.get(), request.getGravity());
            return new ResponseEntity<>(reading, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReadingById(@PathVariable String id) {
        try {
            readingService.deleteReadingById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
