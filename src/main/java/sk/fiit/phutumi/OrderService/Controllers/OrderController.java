package sk.fiit.phutumi.OrderService.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.OrderService.Repositories.OrderRepository;
import sk.fiit.phutumi.OrderService.Repositories.ShoppingCardRepository;
import sk.fiit.phutumi.OrderService.Models.Order;
import sk.fiit.phutumi.OrderService.Models.ShoppingCart;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderRepository orderRepository;
    public static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());


    @PostMapping(value = "/order")
    public ResponseEntity<Order> createNewOrder() {
        try {
            //create new order
            Order newOrder = orderRepository.save(new Order());
            LOGGER.log(Level.INFO, "--- New Order created");
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "--- Did not create new Order");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}