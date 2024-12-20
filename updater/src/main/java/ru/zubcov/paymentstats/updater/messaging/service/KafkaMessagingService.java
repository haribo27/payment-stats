package ru.zubcov.paymentstats.updater.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;
import ru.zubcov.paymentstats.updater.service.PaymentService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessagingService {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;


    @Transactional
    @KafkaListener(topics = "${kafka-topic}")
    public void createPayment(String json) throws JsonProcessingException {
        log.info("Message consumed {}", json);
        paymentService.savePayment(objectMapper.readValue(json, PaymentDto.class));
    }
}
