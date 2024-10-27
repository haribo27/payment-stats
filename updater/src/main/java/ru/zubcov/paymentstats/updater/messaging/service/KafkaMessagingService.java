package ru.zubcov.paymentstats.updater.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;
import ru.zubcov.paymentstats.updater.service.PaymentService;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaMessagingService {

    private static final String topicPayments = "${kafka-topic}";
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;


    @Transactional
    @KafkaListener(topics = topicPayments)
    public void createPayment(String json) throws JsonProcessingException {
        log.info("Message consumed {}", json);
        paymentService.savePayment(objectMapper.readValue(json, PaymentDto.class));
    }
}
