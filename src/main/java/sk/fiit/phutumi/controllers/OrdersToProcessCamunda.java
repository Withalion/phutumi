package sk.fiit.phutumi.controllers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.List;

@Named("orderToProcess")
@Service
public class OrdersToProcessCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        Mono<List> orders = client.get().uri("/ordersToProcess").retrieve().bodyToMono(List.class);
        List ordersList = orders.block();

        // ukazka json
        //[{"label":"Burger King","value":"2"},{"label":"McDonald's","value":"1"},{"label":"Pizza Hut","value":"3"}]

        String textToJson = "[";
        for (int i=0; i<ordersList.size();i++){
            String value = ((LinkedHashMap<?, ?>) ordersList.get(i)).get("id").toString();
            String label = "Order "+value;
            textToJson = textToJson.concat("{ \"label\": \""+label+"\", \"value\": \""+value+"\"}");
            if(i!=ordersList.size()-1){
                textToJson = textToJson.concat(",");
            }
        }
        textToJson = textToJson.concat("]");


        JsonValue jsonValue = SpinValues.jsonValue(textToJson).create();;
        System.out.println(jsonValue.getValue());
        delegateExecution.setVariable("orders", jsonValue);
    }
}
