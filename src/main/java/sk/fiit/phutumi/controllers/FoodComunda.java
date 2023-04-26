package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.inject.Named;

@Named("print")
@Service
public class FoodComunda implements TaskListener {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void notify(final DelegateTask delegateTask){
        System.out.println("Hello");
    }
}
