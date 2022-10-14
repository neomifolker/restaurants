package com.example.restaurants.controller;

import com.example.restaurants.model.Restaurant;
import com.example.restaurants.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestParam(required = false) String name){
        try{
            List<Restaurant> restaurants = new ArrayList<Restaurant>();
            if(name == null)
              restaurantRepository.findAll().forEach(restaurants::add);

            else
                restaurantRepository.findByNameContaining(name).forEach(restaurants::add);

            if (restaurants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(restaurants, HttpStatus.OK);

        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getTutorialById(@PathVariable("id") long id){
        Optional<Restaurant>restaurantData = restaurantRepository.findAllById(id);
        if(restaurantData.isPresent() ){
            return new ResponseEntity<>(restaurantData.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try{
            Restaurant _restaurant = restaurantRepository
                    .save(new Restaurant(restaurant.getName(), restaurant.getDescription()));
            return new ResponseEntity<>(_restaurant, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") long id, @RequestBody Restaurant restaurant) {
        Optional<Restaurant> restaurantData = restaurantRepository.findById(id);
        if (restaurantData.isPresent()){
            Restaurant _restaurant = restaurantData.get();
            _restaurant.setName(restaurant.getName());
            _restaurant.setDescription(restaurant.getDescription());
            return new ResponseEntity<>(restaurantRepository.save(_restaurant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurants(@PathVariable("id") long id) {
        try{
            restaurantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



