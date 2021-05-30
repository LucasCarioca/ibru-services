package net.lucasdesouza.ibru.controllers;

import net.lucasdesouza.ibru.models.Brew;
import net.lucasdesouza.ibru.models.CreateBrewRequest;
import net.lucasdesouza.ibru.services.BrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/brews")
public class BrewsController {

    private final BrewService brewService;

    @Autowired
    BrewsController(BrewService brewService) {
        this.brewService = brewService;
    }

    @GetMapping
    public ResponseEntity<List<Brew>> getAllBrews() {
        return new ResponseEntity<>(brewService.getAllBrews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brew> getBrewById(@PathVariable String id) {
        Optional<Brew> brew = brewService.getBrewById(id);
        if (brew.isPresent()) {
            return new ResponseEntity<>(brew.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBrewById(@PathVariable String id) {
        brewService.deleteBrewById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Brew> postNewBrew(@RequestBody CreateBrewRequest request) {
        Brew brew = brewService.createNewBrew(request);
        return new ResponseEntity<>(brew, HttpStatus.OK);
    }
}
