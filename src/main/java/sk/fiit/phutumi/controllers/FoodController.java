package sk.fiit.phutumi.controllers;

import lombok.RequiredArgsConstructor;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.FoodRepository;
import sk.fiit.phutumi.Repository.MenuRepository;
import sk.fiit.phutumi.Repository.RestaurantRepository;
import sk.fiit.phutumi.models.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class FoodController {   // implements JavaDelegate

    private final FoodRepository foodRepository;

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    public static final Logger LOGGER = Logger.getLogger(FoodController.class.getName());


    @GetMapping("/phutumi/food")
    public ResponseEntity<List<Food>> foodOffer(@RequestParam Map<String, String> restaurantId) {

        Long resId = Long.valueOf(restaurantId.get("restaurantId"));
        List<Menu> menus = menuRepository.findOnlyFoodIdsByRestaurantId(resId);

        List<Long> foodIds = new ArrayList<>();

        if (!menus.isEmpty()) {
            for (Menu menu : menus) {
                foodIds.add(menu.getFoodId());
            }

            List<Food> allFoods = foodRepository.findFoodsByIdIn(foodIds);

            return new ResponseEntity<>(allFoods, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/phutumi/food", consumes = "application/json")
    public ResponseEntity<Menu> addFood(@RequestBody FoodAdder foodToAdd) {

        String foodName = foodToAdd.getFoodName();
        Long restaurantId = foodToAdd.getRestaurantId();

        if(!foodName.isEmpty() && restaurantId != null){
            Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
            if (restaurant.isPresent()){
                try{
                    Food newFood = foodRepository.save(new Food(foodName));
                    LOGGER.log(Level.SEVERE, "--- new food and menu created");

                    Menu newMenu = menuRepository.save(new Menu(restaurantId, newFood.getId()));

                    return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
                }catch(Exception e){
                    LOGGER.log(Level.SEVERE, "--- new food not created");
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                LOGGER.log(Level.SEVERE, "--- restaurantId non existent, new food not created");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        }else{
            LOGGER.log(Level.SEVERE, "--- no name in request body, new food not created");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Override
//    public void execute(DelegateExecution delegateExecution) throws Exception {
//        LOGGER.info("--------- calculating interest of the loan");
//    }
}