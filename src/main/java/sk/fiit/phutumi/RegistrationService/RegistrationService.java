package sk.fiit.phutumi.RegistrationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableEurekaServer
public class RegistrationService {

    public static void main(String[] args) {
        // Tell server to look for registration-service.yml
        System.setProperty("spring.config.name", "registration-service");

        SpringApplication.run(RegistrationService.class, args);
    }
}
