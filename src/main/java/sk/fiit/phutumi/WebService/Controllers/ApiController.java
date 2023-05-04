package sk.fiit.phutumi.WebService.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.fiit.phutumi.WebService.Models.AddToShoppingCartEvent;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController()
@RequestMapping(path = "/phutumi/api")
public class ApiController {
    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, AddToShoppingCartEvent> kafkaObjectTemplate;
    public static final Logger LOGGER = Logger.getLogger(ApiController.class.getName());


    @GetMapping("/processOrder")
    public ResponseEntity<Void> processOrderProxy(@RequestParam("orderId") Long orderId){
        LOGGER.log(Level.INFO, "Processing new order " + orderId + "!");
        CompletableFuture<SendResult<String, String>> promise = kafkaTemplate.send("processOrder", orderId.toString());
        promise.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                System.out.println("Event successfully registered, result: " + sendResult);
            }
        });
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/addToShoppingCart")
    public ResponseEntity<Void> addToShoppingCartProxy(@RequestParam("foodId") Long foodId, @RequestParam("orderId") Long orderId){
        LOGGER.log(Level.INFO, "Adding new item " + foodId + " to cart " + orderId + "!");
        CompletableFuture<SendResult<String, AddToShoppingCartEvent>> promise =
                kafkaObjectTemplate.send("addToShoppingCart", new AddToShoppingCartEvent(foodId, orderId));
        promise.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                System.out.println("Event successfully registered, result: " + sendResult);
            }
        });
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @GetMapping("/payOrder")
    public ResponseEntity<Void> payOrderProxy(@RequestParam("orderId") Long orderId){
        LOGGER.log(Level.INFO, "Paying order " + orderId + "!");
        CompletableFuture<SendResult<String, String>> promise = kafkaTemplate.send("payOrder", orderId.toString());
        promise.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                System.out.println("Event successfully registered, result: " + sendResult);
            }
        });
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
