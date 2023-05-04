package sk.fiit.phutumi.PaymentService.Controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
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

@AllArgsConstructor
@Controller
public class PaymentKafkaController {
    private final OrderRepository orderRepository;
    public static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    @KafkaListener(topics = "payOrder", groupId = "phutumi")
    public void payOrder(String eventData) {

        Order orderToPay = orderRepository.findById(Long.valueOf(eventData)).orElse(null);

        if (orderToPay == null){
            LOGGER.log(Level.INFO, "--- not found order: "+ eventData);
        }
        else if (!orderToPay.isPaid()) {
            orderToPay.setPaid(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderToPay.getId());

        }
        else {
            LOGGER.log(Level.INFO, "--- already paid order: " + orderToPay.getId());
        }
    }

    @KafkaListener(topics = "processOrder", groupId = "phutumi")
    public void processOrder(String eventData) {

        Order orderToPay = orderRepository.findById(Long.valueOf(eventData)).orElse(null);

        if (orderToPay == null){
            LOGGER.log(Level.INFO, "--- not found order: "+ eventData);
        }
        else if (!orderToPay.isProcessed()) {
            orderToPay.setProcessed(true);
            orderRepository.save(orderToPay);
            LOGGER.log(Level.INFO, "--- payment processed for order: "+ orderToPay.getId());
        }
    }
}
