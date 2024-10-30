package ru.zubcov.paymentstats.updater.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.topic.partitions}")
    private int partitions;

    @Value("${kafka.topic.replication-factor}")
    private short replicationFactor;

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(topicName, partitions, replicationFactor);
    }
}
