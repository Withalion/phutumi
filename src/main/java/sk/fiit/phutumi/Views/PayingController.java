package sk.fiit.phutumi.Views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.models.Order;

import java.util.List;
import java.util.Map;

@Controller
public class PayingController {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");


    @GetMapping("/phutumi/shoppingCart")
    public String payingPage(@RequestParam("id") Long orderId, Model model){
        Mono<List> foods = client.get().uri(uriBuilder -> uriBuilder
                .path("/getOrderFoods")
                .queryParam("orderId", orderId)
                .build()).retrieve().bodyToMono(List.class);
        Mono<Order> order = client.get().uri(uriBuilder -> uriBuilder
                .path("/getOrder")
                .queryParam("orderId", orderId)
                .build()).retrieve().bodyToMono(Order.class);
        try{
            model.addAttribute("foods", foods.block());
            model.addAttribute("order", order.block());
            return "shoppingCartPage";
        }catch (WebClientResponseException.NotFound err){
            return "notFound";
        }catch (WebClientResponseException.BadRequest err2){
            return "badRequest";
        }
    }

    @GetMapping("/phutumi/orders")
    public String ordersPage(Model model){
        Mono<List> orders = client.get().uri("/ordersToProcess").retrieve().bodyToMono(List.class);
        model.addAttribute("orders", orders.block());
        return "ordersPage";
    }
}
