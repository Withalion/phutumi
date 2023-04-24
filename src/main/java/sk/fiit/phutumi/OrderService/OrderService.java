package sk.fiit.phutumi.OrderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderService {

    public static void main(String[] args) {
        // Tell server to look for order-service.yml
        System.setProperty("spring.config.name", "order-service");
        SpringApplication.run(OrderService.class, args);
    }
}
