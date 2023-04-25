package sk.fiit.phutumi.controllers;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Named("hello")
@Service
public class Hello implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        Mono<List> restaurants = client.get().uri("/restaurants").retrieve().bodyToMono(List.class);
        String var = restaurants.block().toString();
        System.out.println(var);
        delegateExecution.setVariable("input", var);
    }
}
