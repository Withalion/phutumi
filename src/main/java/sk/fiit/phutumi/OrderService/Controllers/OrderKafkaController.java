package sk.fiit.phutumi.OrderService.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import sk.fiit.phutumi.OrderService.Models.AddToShoppingCartEvent;
import sk.fiit.phutumi.OrderService.Repositories.OrderRepository;
import sk.fiit.phutumi.OrderService.Repositories.ShoppingCardRepository;
import sk.fiit.phutumi.OrderService.Models.Order;
import sk.fiit.phutumi.OrderService.Models.ShoppingCart;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Controller
public class OrderKafkaController {
    private final OrderRepository orderRepository;
    private final ShoppingCardRepository shoppingCardRepository;
    public static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @KafkaListener(topics = "addToShoppingCart", groupId = "phutumi")
    public void shoppingCard(AddToShoppingCartEvent eventData) {


        Optional<Order> orderDB = orderRepository.findById(eventData.getOrderId());
        if (orderDB.isEmpty()) {
            LOGGER.log(Level.SEVERE, "--- order ID provided in request body is not in DB");
        } else if (orderDB.get().isProcessed() || orderDB.get().isPaid()) {
            LOGGER.log(Level.INFO, "--- order already processed or payed for or both");
        }

        try {
            //create new shopping cart (order_food record)
            shoppingCardRepository.save( new ShoppingCart(eventData.getFoodId(), eventData.getOrderId()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "--- {Probably} food ID provided in request body is not in DB");
        }
    }
}