package sk.fiit.phutumi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FoodController {

    Map <String, String> foodList = new HashMap<>();

    @GetMapping("/food")
    public Map<String,String> foodOffer(@RequestParam Map<String, String> restaurantId){
        System.out.println(restaurantId);

        foodList.put("1", "Halusky");
        foodList.put("2", "Rezen");
        foodList.put("3", "Dukatove Buchticky");
        foodList.put("4", "Parene Buchty");
        foodList.put("5", "Spagety");
        foodList.put("6", "Sushi");

        return foodList;
    }
}
