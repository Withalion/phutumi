package sk.fiit.phutumi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.OrderRepository;
import sk.fiit.phutumi.Repository.ShoppingCardRepository;
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
    public static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    @RequestMapping(value = "/phutumi/payOrder", method = RequestMethod.GET)

    public @ResponseBody ResponseEntity<Order> getItem(@RequestParam("orderId") Long orderId) {

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        if (orderToPay == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!orderToPay.getProcessed()){

            orderToPay.setProcessed(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment confirmed for order: "+ orderId);

            return new ResponseEntity<>(orderToPay, HttpStatus.OK);

        }
        else {
            LOGGER.log(Level.INFO, "--- payment already processed for order: " + orderId);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
