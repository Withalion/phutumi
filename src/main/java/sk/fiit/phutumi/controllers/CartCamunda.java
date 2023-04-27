package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.models.Order;
import sk.fiit.phutumi.models.ShoppingCart;

import javax.inject.Named;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;

@Named("cart")
@Service
public class CartCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    public void execute(DelegateExecution delegateExecution){

        String orderId = delegateExecution.getVariable("order").toString();
        String restaurantId = delegateExecution.getVariable("restaurant").toString();
        String foodId = delegateExecution.getVariable("food").toString();



        Mono<ShoppingCart> shoppingCartMono = client.get().uri("/addToShoppingCart?foodId=" + foodId + "&orderId=" + orderId).retrieve().bodyToMono(ShoppingCart.class);;
        ShoppingCart shoppingCart = shoppingCartMono.block();
        // ukazka json
        //[{"label":"Burger King","value":"2"},{"label":"McDonald's","value":"1"},{"label":"Pizza Hut","value":"3"}]

        delegateExecution.setVariable("restaurant", restaurantId);
        delegateExecution.setVariable("order", orderId);
    }
}
