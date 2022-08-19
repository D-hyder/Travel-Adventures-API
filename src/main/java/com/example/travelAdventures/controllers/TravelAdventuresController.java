package com.example.travelAdventures.controllers;

import com.example.travelAdventures.models.Adventure;
import com.example.travelAdventures.repositories.AdventureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import java.util.Iterable;
import java.util.Optional;

//import org.springframework.web.bind.annotation.ResponseStatusException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/traveladventures")
public class TravelAdventuresController {

    private final AdventureRepository adventureRepository;

    public TravelAdventuresController(AdventureRepository adventureRepo) {
        this.adventureRepository = adventureRepo;
    }

    @GetMapping()
    public Iterable<Adventure> getAllAdventures() {
        return this.adventureRepository.findAll();
    }

    @GetMapping("/bycountry/{country}")
    public List<Adventure> getAdventureByCountry(@PathVariable("country") String country) {
        return this.adventureRepository.findByCountry(country);
    }

    @GetMapping("/bystate")
    public List<Adventure> getAdventureByState(@RequestParam(name="state") String state) {
        return this.adventureRepository.findByState(state);
    }

    @PostMapping()
    public Adventure createNewAdventure(@RequestBody Adventure adventure) {
        Adventure newAdventure = this.adventureRepository.save(adventure);
        return newAdventure;
    }

    @PutMapping("/{id}")
    public Adventure modifyAdventure(@PathVariable("id") Integer id, @RequestBody Adventure adventure) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if(!adventureOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Adventure adventureToUpdate = adventureOptional.get();
        if(adventureToUpdate.getBlogCompleted()) {
            adventureToUpdate.setBlogCompleted(false);
        } else {
            adventureToUpdate.setBlogCompleted(true);
        }
        Adventure updatedAdventure = this.adventureRepository.save(adventureToUpdate);
        return updatedAdventure;
    }

    @DeleteMapping("/{id}")
    public void createNewAdventure(@PathVariable("id") Integer id) {
        Optional<Adventure> toRemoveAdventureOpt = this.adventureRepository.findById(id);
        if(!toRemoveAdventureOpt.isPresent()) {
            Adventure toRemoveAdventure = toRemoveAdventureOpt.get();
            this.adventureRepository.delete(toRemoveAdventure);
        }
    }
}
