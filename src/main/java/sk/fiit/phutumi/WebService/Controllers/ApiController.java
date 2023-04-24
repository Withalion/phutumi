package sk.fiit.phutumi.WebService.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController()
@RequestMapping(path = "/phutumi/api")
public class ApiController {
    private final String orderServiceURL;
    private final String paymentServiceURL;
    private final WebClient.Builder webclientBuilder;

    @GetMapping("/processOrder")
    public ResponseEntity<Void> processOrderProxy(@RequestParam("orderId") Long orderId){
        return webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(paymentServiceURL)
                .path("/processOrder")
                .queryParam("orderId", orderId)
                .build()).retrieve().toBodilessEntity().block();
    }

    @GetMapping("/addToShoppingCart")
    public ResponseEntity<Void> addToShoppingCartProxy(@RequestParam("foodId") Long foodId, @RequestParam("orderId") Long orderId){
        return webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(orderServiceURL)
                .path("/addToShoppingCart")
                .queryParam("foodId", foodId)
                .queryParam("orderId", orderId)
                .build()).retrieve().toBodilessEntity().block();
    }

    @GetMapping("/payOrder")
    public ResponseEntity<Void> payOrderProxy(@RequestParam("orderId") Long orderId){
        return webclientBuilder.build().get().uri(uriBuilder -> uriBuilder
                .host(paymentServiceURL)
                .path("/payOrder")
                .queryParam("orderId", orderId)
                .build()).retrieve().toBodilessEntity().block();
    }
}
