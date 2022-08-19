package com.example.travelAdventures.repositories;

import java.util.List;

import com.example.travelAdventures.models.Adventure;
import org.springframework.data.repository.CrudRepository;

public interface AdventureRepository extends CrudRepository<Adventure, Integer> {
    public List<Adventure> findByCountry(String country);
    public List<Adventure> findByState(String state);
}