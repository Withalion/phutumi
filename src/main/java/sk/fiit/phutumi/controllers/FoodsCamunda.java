package sk.fiit.phutumi.controllers;

import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;

@Named("foods")
@Service
public class FoodsCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");

//
    @Override
    public void execute(DelegateExecution delegateExecution){
        String restaurantId = delegateExecution.getVariable("restaurant").toString();
        String orderId = delegateExecution.getVariable("order").toString();
        System.out.println("Restaurant ID: " + restaurantId);

        Mono<List> foods = client.get().uri("/food?restaurantId=" + restaurantId).retrieve().bodyToMono(List.class);
        List foodList = foods.block();

        String textToJson = "[";
        for (int i=0; i<foodList.size();i++){
            String value = ((LinkedHashMap<?, ?>) foodList.get(i)).get("id").toString();
            String label = (String) ((LinkedHashMap<?, ?>) foodList.get(i)).get("name");
            textToJson = textToJson.concat("{ \"label\": \""+label+"\", \"value\": \""+value+"\"}");
            if(i!=foodList.size()-1){
                textToJson = textToJson.concat(",");
            }
        }
        textToJson = textToJson.concat("]");
        JsonValue jsonValue = SpinValues.jsonValue(textToJson).create();;
        System.out.println(jsonValue.getValue());
        delegateExecution.setVariable("foods", jsonValue);
        delegateExecution.setVariable("restaurant", restaurantId);
        delegateExecution.setVariable("order", orderId);
    }
}
