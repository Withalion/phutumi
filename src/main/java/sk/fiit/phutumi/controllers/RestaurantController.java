package sk.fiit.phutumi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.fiit.phutumi.Repository.RestaurantRepository;

import sk.fiit.phutumi.models.Restaurant;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public static final Logger LOGGER = Logger.getLogger(RestaurantController.class.getName());

    @GetMapping("/phutumi/restaurants")
    public ResponseEntity<List<Restaurant>> restaurantsOffer() {

        List<Restaurant> restaurants = restaurantRepository.findAll();

        if (!restaurants.isEmpty()){
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
