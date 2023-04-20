package sk.fiit.phutumi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.FoodRepository;
import sk.fiit.phutumi.Repository.OrderRepository;
import sk.fiit.phutumi.Repository.ShoppingCardRepository;
import sk.fiit.phutumi.models.Food;
import sk.fiit.phutumi.models.Order;
import sk.fiit.phutumi.models.ShoppingCart;

import java.util.*;
import java.util.function.LongToIntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PaymentController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShoppingCardRepository shoppingCardRepository;
    @Autowired
    FoodRepository foodRepository;
    public static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    @RequestMapping(value = "/phutumi/getOrder", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Order> getOrder(@RequestParam("orderId") Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null){
            LOGGER.log(Level.INFO, "--- order not found: " + orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        LOGGER.log(Level.INFO, "--- retrieving order: " + orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @RequestMapping(value = "/phutumi/getOrderFoods", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Food>> getOrderFoods(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if (!orderToPay.getPaid()){

            List<ShoppingCart> shoppingCarts =  shoppingCardRepository.findOnlyFoodIdsByOrderId(orderId);
            List<Long> foodIds = new ArrayList<>();
            List<Food> foods = new ArrayList<>();
            if (!shoppingCarts.isEmpty()) {
                for (ShoppingCart shoppingCart : shoppingCarts) {
                    foodIds.add(shoppingCart.getFoodId());
                }
                for(Long food_id : foodIds){
                    Optional<Food> f = foodRepository.findById(food_id);
                    if(f.isPresent()){
                        foods.add(f.get());
                    }
                }
//                foods = foodRepository.findAllById(foodIds);
            } else {
                foods = null;
            }
            LOGGER.log(Level.INFO, "--- retrieving foods for order: " + orderId);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
        else {
            LOGGER.log(Level.INFO, "--- payment already done for order: " + orderId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/phutumi/payOrder", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Order> payOrder(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            LOGGER.log(Level.INFO, "--- not found order: "+ orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!orderToPay.getPaid()) {

            orderToPay.setPaid(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderId);

            return new ResponseEntity<>(orderToPay, HttpStatus.OK);
        }
        LOGGER.log(Level.INFO, "--- already paid order: "+ orderId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/phutumi/processOrder", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Order> processOrder(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!orderToPay.getProcessed()) {

            orderToPay.setProcessed(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderId);

            return new ResponseEntity<>(orderToPay, HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping(value = "/phutumi/ordersToProcess", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List> getOrdersToProcess() {

        List<Order> orders = orderRepository.findAll();
        List<Order> ordersToReturn = new ArrayList<>();

        if (orders == null){
            LOGGER.log(Level.INFO, "--- no orders to retrive");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        for(Order order: orders){
            if (!order.getProcessed() && order.getPaid()) {
                ordersToReturn.add(order);
            }
        }
        if(ordersToReturn == null){
            LOGGER.log(Level.INFO, "--- no paid orders to retrive");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        LOGGER.log(Level.INFO, "--- retrieved paid orders");
        return new ResponseEntity<>(ordersToReturn, HttpStatus.OK);
    }

}
