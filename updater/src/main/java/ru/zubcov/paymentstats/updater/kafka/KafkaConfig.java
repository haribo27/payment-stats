package ru.zubcov.paymentstats.updater.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("payment", 1, (short) 1);
    }
}
