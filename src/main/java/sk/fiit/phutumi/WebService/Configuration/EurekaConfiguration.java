package sk.fiit.phutumi.WebService.Configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import sk.fiit.phutumi.WebService.Controllers.OrderingController;
import sk.fiit.phutumi.WebService.Controllers.PayingController;

@Configuration
public class EurekaConfiguration {
    public final String offerServiceURL = "offer-service";
    public final String orderServiceURL = "order-service";
    public final String paymentServiceURL = "payment-service";

    //Create Webclient.builder which will be able to find registered services from logical URL
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

}
