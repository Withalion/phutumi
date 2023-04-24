package sk.fiit.phutumi.PaymentService.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.PaymentService.Models.Food;
import sk.fiit.phutumi.PaymentService.Models.Order;
import sk.fiit.phutumi.PaymentService.Models.ShoppingCart;
import sk.fiit.phutumi.PaymentService.Repositories.FoodRepository;
import sk.fiit.phutumi.PaymentService.Repositories.OrderRepository;
import sk.fiit.phutumi.PaymentService.Repositories.ShoppingCardRepository;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final OrderRepository orderRepository;
    private final ShoppingCardRepository shoppingCardRepository;
    private final FoodRepository foodRepository;
    public static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    @GetMapping(value = "/getOrder")
    public @ResponseBody ResponseEntity<Order> getOrder(@RequestParam("orderId") Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null){
            LOGGER.log(Level.INFO, "--- order not found: " + orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        LOGGER.log(Level.INFO, "--- retrieving order: " + orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping(value = "/getOrderFoods")
    public @ResponseBody ResponseEntity<List<Food>> getOrderFoods(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if (!orderToPay.isPaid()){

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
    @GetMapping(value = "/payOrder")
    public @ResponseBody ResponseEntity<Order> payOrder(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            LOGGER.log(Level.INFO, "--- not found order: "+ orderId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!orderToPay.isPaid()) {

            orderToPay.setPaid(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderId);

            return new ResponseEntity<>(orderToPay, HttpStatus.OK);
        }
        LOGGER.log(Level.INFO, "--- already paid order: "+ orderId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/processOrder")
    public @ResponseBody ResponseEntity<Order> processOrder(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!orderToPay.isProcessed()) {

            orderToPay.setProcessed(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderId);

            return new ResponseEntity<>(orderToPay, HttpStatus.OK);
        }
        return null;
    }

    @GetMapping(value = "/ordersToProcess")
    public @ResponseBody ResponseEntity<List> getOrdersToProcess() {

        List<Order> orders = orderRepository.findAll();
        List<Order> ordersToReturn = new ArrayList<>();

        if (orders.isEmpty()){
            LOGGER.log(Level.INFO, "--- no orders to retrieve");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        for(Order order: orders){
            if (!order.isProcessed() && order.isPaid()) {
                ordersToReturn.add(order);
            }
        }
        if(ordersToReturn.isEmpty()){
            LOGGER.log(Level.INFO, "--- no paid orders to retrieve");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        LOGGER.log(Level.INFO, "--- retrieved paid orders");
        return new ResponseEntity<>(ordersToReturn, HttpStatus.OK);
    }

}
