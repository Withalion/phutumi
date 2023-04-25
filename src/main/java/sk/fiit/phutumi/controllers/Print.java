package sk.fiit.phutumi.controllers;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Named("print")
@Service
public class Print implements TaskListener {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void notify(final DelegateTask delegateTask){
        System.out.println("Listener: ".concat(delegateTask.getAssignee()));
    }
}
