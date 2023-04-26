package sk.fiit.phutumi.controllers;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.Spin;
import org.camunda.spin.json.SpinJsonNode;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Named("printrest")
@Service
public class PrintRest implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        String json = delegateExecution.getVariable("input").toString();
        System.out.println(json);
    }
}
