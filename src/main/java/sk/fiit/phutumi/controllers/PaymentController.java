package sk.fiit.phutumi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.fiit.phutumi.Repository.OrderRepository;
import sk.fiit.phutumi.Repository.ShoppingCardRepository;
import sk.fiit.phutumi.models.Order;
import sk.fiit.phutumi.models.ShoppingCart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
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

    @RequestMapping(value = "shoppingCart", method = RequestMethod.GET, consumes = "application/json")

    public @ResponseBody Order getItem(@RequestParam("orderId") Long orderId) {
        //System.out.println(orderId);
        LOGGER.log(Level.INFO, "--- payment request for order: " + orderId);

        Order orderToPay = orderRepository.findById(orderId).orElse(null);

        System.out.println("Do you want to pay for the order (y=yes)");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        if (input == "y") {
            LOGGER.log(Level.INFO, "--- payment confirmed for order: " + orderId);
        } else {
            LOGGER.log(Level.INFO, "--- payment cancelled for order: " + orderId);
        }

        return orderToPay;
    }
}
