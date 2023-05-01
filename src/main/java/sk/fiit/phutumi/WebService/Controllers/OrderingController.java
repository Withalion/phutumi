package sk.fiit.phutumi.WebService.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.WebService.Models.Order;

import java.util.List;

@Controller
@RequestMapping(path = "/phutumi")
public class OrderingController {
    private final String offerServiceURL = "offer-service";
    private final String orderServiceURL = "order-service";
    private final Builder webclientBuilder;

    @GetMapping("")
    public String mainPage(Model model){
        Mono<List> restaurants = webclientBuilder.build().get().uri("http://" + offerServiceURL + "/restaurants")
                .retrieve().bodyToMono(List.class);
        model.addAttribute("restaurants", restaurants.block());
        return "mainPage";
    }

    @GetMapping("/restaurant")
    public String restaurantPage(@RequestParam("id") Long restaurantId, Model model){
        Mono<Order> order = webclientBuilder.build().post().uri("http://" + orderServiceURL + "/order").retrieve()
                .bodyToMono(Order.class);
        Mono<List> foods = webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(offerServiceURL)
                .path("/food")
                .queryParam("restaurantId", restaurantId)
                .build()).retrieve().bodyToMono(List.class);

        try{
            model.addAttribute("foods", foods.block());
            model.addAttribute("order", order.block());
            return "restaurantPage";
        }catch (WebClientResponseException.NotFound err){
            return "notFound";
        }catch (WebClientResponseException.BadRequest err2){
            return "badRequest";
        }
    }

    public OrderingController(Builder webclientBuilder) {
        this.webclientBuilder = webclientBuilder;
    }
}
