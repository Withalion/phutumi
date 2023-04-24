package sk.fiit.phutumi.WebService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;
import sk.fiit.phutumi.WebService.Controllers.ApiController;
import sk.fiit.phutumi.WebService.Controllers.OrderingController;
import sk.fiit.phutumi.WebService.Controllers.PayingController;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class WebService {
    public final String offerServiceURL = "offer-service";
    public final String orderServiceURL = "order-service";
    public final String paymentServiceURL = "payment-service";

    public static void main(String[] args){
        // Tell server to look for web-service.yml
        System.setProperty("spring.config.name", "web-service");
        SpringApplication.run(WebService.class, args);
    }

    //Create Webclient.builder which will be able to find registered services from logical URL
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public OrderingController orderingController(){
        return new OrderingController(offerServiceURL, orderServiceURL, loadBalancedWebClientBuilder());
    }

    @Bean
    public PayingController payingController(){
        return new PayingController(paymentServiceURL, loadBalancedWebClientBuilder());
    }

    @Bean
    public ApiController apiController(){
        return new ApiController(orderServiceURL, paymentServiceURL, loadBalancedWebClientBuilder());
    }
}
