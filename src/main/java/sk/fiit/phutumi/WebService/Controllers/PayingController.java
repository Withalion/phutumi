package sk.fiit.phutumi.WebService.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import sk.fiit.phutumi.WebService.Models.Order;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/phutumi")
public class PayingController {
    private final String paymentServiceURL;
    private final WebClient.Builder webclientBuilder;

    @GetMapping("/shoppingCart")
    public String payingPage(@RequestParam("id") Long orderId, Model model){
        Mono<List> foods = webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(paymentServiceURL)
                .path("/getOrderFoods")
                .queryParam("orderId", orderId)
                .build()).retrieve().bodyToMono(List.class);
        Mono<Order> order = webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(paymentServiceURL)
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

    @GetMapping("/orders")
    public String ordersPage(Model model){
        Mono<List> orders = webclientBuilder.build().get().uri("http://" + paymentServiceURL + "/ordersToProcess")
                .retrieve().bodyToMono(List.class);
        model.addAttribute("orders", orders.block());
        return "ordersPage";
    }

}
