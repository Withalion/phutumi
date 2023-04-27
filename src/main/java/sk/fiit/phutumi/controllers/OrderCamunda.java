package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.models.Order;

import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.List;

@Named("order")
@Service
public class OrderCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        String restaurantId = delegateExecution.getVariable("restaurant").toString();

        Mono<Order> order = client.post().uri(uriBuilder -> uriBuilder.path("/order").build()).retrieve().bodyToMono(Order.class);
        Order orderVal = order.block();

        String orderId = orderVal.getId().toString();

        delegateExecution.setVariable("order", orderId);
        delegateExecution.setVariable("restaurant", restaurantId);
    }
}
