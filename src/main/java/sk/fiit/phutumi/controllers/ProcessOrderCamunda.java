package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.models.Order;

import javax.inject.Named;

@Named("processOrder")
@Service
public class ProcessOrderCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        String orderId = delegateExecution.getVariable("order").toString();
        Mono<Order> orderMono =client.get().uri("/processOrder?orderId="+orderId).retrieve().bodyToMono(Order.class);
        Order order = orderMono.block();
    }
}
