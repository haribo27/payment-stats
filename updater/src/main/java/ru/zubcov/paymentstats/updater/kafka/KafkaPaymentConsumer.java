package ru.zubcov.paymentstats.updater.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentConsumer {

    @KafkaListener(topics = "payment")
    public void listen(String message) {
    }
}
