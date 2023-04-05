package sk.fiit.phutumi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.OrderRepository;
import sk.fiit.phutumi.Repository.ShoppingCardRepository;
import sk.fiit.phutumi.models.Order;
import sk.fiit.phutumi.models.ShoppingCart;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ShoppingCardRepository shoppingCardRepository;

    public static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @PostMapping(value = "/shoppingCart", consumes = "application/json")
    public ResponseEntity<ShoppingCart> shoppingCard(@RequestBody ShoppingCart shoppingCart) {

        Long foodId = shoppingCart.getFoodId();
        Long orderId = shoppingCart.getOrderId();

        // if order in request body is null
        if (orderId == null) {
            try {
                Order newOrder = orderRepository.save(new Order());
                orderId = newOrder.getId();
                LOGGER.log(Level.INFO, "--- Order created");
//                return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "--- Order not created");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Optional<Order> orderDB = orderRepository.findById(orderId);
            if (orderDB.isEmpty()) {
                LOGGER.log(Level.SEVERE, "--- order ID provided in request body is not in DB");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        try {
            //create new shopping cart (order_food record)
            ShoppingCart newShoppingCart = shoppingCardRepository.save(new ShoppingCart(foodId, orderId));
            return new ResponseEntity<>(newShoppingCart, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "--- {Probably} food ID provided in request body is not in D}");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
}