package sk.fiit.phutumi.Views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Controller
public class OrderingController {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");

    @GetMapping("/phutumi")
    public String mainPage(Model model){
        Mono<List> restaurants = client.get().uri("/restaurants").retrieve().bodyToMono(List.class);
        model.addAttribute("restaurants", restaurants.block());
        return "mainPage";
    }

    @GetMapping("/phutumi/restaurant")
    public String restaurantPage(@RequestParam("id") Long restaurantId, Model model){
        Mono<List> order = client.post().uri(uriBuilder -> uriBuilder
                .path("/order")
                .build()).retrieve().bodyToMono(List.class);
        Mono<List> foods = client.get().uri(uriBuilder -> uriBuilder
                .path("/food")
                .queryParam("restaurantId", restaurantId)
                .build()).retrieve().bodyToMono(List.class);
        try{
            model.addAttribute("order", order.block());
            model.addAttribute("foods", foods.block());
            return "restaurantPage";
        }catch (WebClientResponseException.NotFound err){
            return "notFound";
        }catch (WebClientResponseException.BadRequest err2){
            return "badRequest";
        }
    }
}
