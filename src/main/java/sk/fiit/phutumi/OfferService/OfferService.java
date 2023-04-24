package sk.fiit.phutumi.OfferService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OfferService {

    public static void main(String[] args) {
        // Tell server to look for offer-service.yml
        System.setProperty("spring.config.name", "offer-service");
        SpringApplication.run(OfferService.class, args);
    }
}
