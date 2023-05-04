package sk.fiit.phutumi.WebService.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic addToShoppingCartTopic(){
        return TopicBuilder.name("addToShoppingCart").build();
    }

    @Bean
    public NewTopic processOrderTopic(){
        return TopicBuilder.name("processOrder").build();
    }

    @Bean
    public NewTopic payOrderTopic(){
        return TopicBuilder.name("payOrder").build();
    }
}
