package com.example.restaurants.repository;

import com.example.restaurants.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByNameContaining(String name);

    Optional<Restaurant> findAllById(long id);

    List<Restaurant> findAll();

}
