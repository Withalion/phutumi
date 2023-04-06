package sk.fiit.phutumi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.FoodRepository;
import sk.fiit.phutumi.Repository.MenuRepository;
import sk.fiit.phutumi.models.Food;
import sk.fiit.phutumi.models.Menu;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class FoodController {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    MenuRepository menuRepository;


    public static final Logger LOGGER = Logger.getLogger(FoodController.class.getName());

    Map<String, String> foodList = new HashMap<>();

    @GetMapping("/food")
    public ResponseEntity<List<Food>> foodOffer(@RequestParam Map<String, String> restaurantId) {

        LOGGER.log(Level.INFO, "---- in GET endpoint /food, restaurant ID: {0}", restaurantId.get("restaurantId"));

//        foodList.put("1", "Halusky");
//        foodList.put("2", "Rezen");
//        foodList.put("3", "Dukatove Buchticky");
//        foodList.put("4", "Parene Buchty");
//        foodList.put("5", "Spagety");
//        foodList.put("6", "Sushi");

        Long resId = Long.valueOf(restaurantId.get("restaurantId"));
        List<Menu> menus = menuRepository.findOnlyFoodIdsByRestaurantId(resId);

        List<Long> foodIds = new ArrayList<>();

        if (!menus.isEmpty()){
            for (Menu menu : menus) {
                foodIds.add(menu.getFoodId());
            }

            List<Food> allFoods = foodRepository.findFoodsByIdIn(foodIds);

            return new ResponseEntity<>(allFoods, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
