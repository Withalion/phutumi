package sk.fiit.phutumi.PaymentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import sk.fiit.phutumi.OrderService.OrderService;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentService {

    public static void main(String[] args) {
        // Tell server to look for payment-service.yml
        System.setProperty("spring.config.name", "payment-service");
        SpringApplication.run(PaymentService.class, args);
    }
}
