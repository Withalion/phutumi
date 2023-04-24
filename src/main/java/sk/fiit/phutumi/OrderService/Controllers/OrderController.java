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

    private final ShoppingCardRepository shoppingCardRepository;

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


    @GetMapping(value = "/addToShoppingCart")
    public ResponseEntity<ShoppingCart> shoppingCard(@RequestParam("foodId") Long foodId, @RequestParam("orderId") Long orderId) {


        Optional<Order> orderDB = orderRepository.findById(orderId);
        if (orderDB.isEmpty()) {
            LOGGER.log(Level.SEVERE, "--- order ID provided in request body is not in DB");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else if (orderDB.get().isProcessed() || orderDB.get().isPaid()) {
            LOGGER.log(Level.INFO, "--- order already processed or payed for or both");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            //create new shopping cart (order_food record)
            ShoppingCart newShoppingCart = shoppingCardRepository.save(new ShoppingCart(foodId, orderId));
            return new ResponseEntity<>(newShoppingCart, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "--- {Probably} food ID provided in request body is not in DB");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
    }
}