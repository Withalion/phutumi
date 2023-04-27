package sk.fiit.phutumi.controllers;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Named("restaurants")
@Service
public class RestaurantsCamunda implements JavaDelegate {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");
    @Override
    public void execute(DelegateExecution delegateExecution){
        Mono<List> restaurants = client.get().uri("/restaurants").retrieve().bodyToMono(List.class);
        List resList = restaurants.block();

        // ukazka json
        //[{"label":"Burger King","value":"2"},{"label":"McDonald's","value":"1"},{"label":"Pizza Hut","value":"3"}]

        String textToJson = "[";
        for (int i=0; i<resList.size();i++){
            String value = ((LinkedHashMap<?, ?>) resList.get(i)).get("id").toString();
            String label = (String) ((LinkedHashMap<?, ?>) resList.get(i)).get("name");
            textToJson = textToJson.concat("{ \"label\": \""+label+"\", \"value\": \""+value+"\"}");
            if(i!=resList.size()-1){
                textToJson = textToJson.concat(",");
            }
        }
        textToJson = textToJson.concat("]");


        JsonValue jsonValue = SpinValues.jsonValue(textToJson).create();;
        System.out.println(jsonValue.getValue());
        delegateExecution.setVariable("restaurantsId", jsonValue);
    }
}
