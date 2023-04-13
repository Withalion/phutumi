package sk.fiit.phutumi.Views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class OrderingController {
    WebClient client = WebClient.create("http://localhost:8080/phutumi");

    @GetMapping("/phutumi")
    public String mainPage(Model model){
        Mono<List> restaurants = client.get().uri("/restaurants").retrieve().bodyToMono(List.class);
        model.addAttribute("restaurants", restaurants.block());
        return "mainPage";
    }
}
