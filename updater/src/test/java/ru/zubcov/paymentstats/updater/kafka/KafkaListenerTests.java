package ru.zubcov.paymentstats.updater.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.zubcov.paymentstats.updater.BaseTestContainerClass;
import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;
import ru.zubcov.paymentstats.updater.service.PaymentService;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
public class KafkaListenerTests extends BaseTestContainerClass {

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testKafkaListenerMethodSuccess() throws JsonProcessingException, InterruptedException {
        PaymentDto paymentDto = new PaymentDto(1, "100", new BigDecimal(500), "51",
                "200", "123456");
        String json = objectMapper.writeValueAsString(paymentDto);
        Mockito.doNothing().when(paymentService).savePayment(Mockito.any());
        kafkaTemplate.send("payment", json);
        Thread.sleep(5000);
        Mockito.verify(paymentService, Mockito.times(1)).savePayment(Mockito.any());
    }

    @Test
    void testKafkaListenerWithInvalidTopic() throws JsonProcessingException, InterruptedException {
        PaymentDto paymentDto = new PaymentDto(1, "100", new BigDecimal(500), "51",
                "200", "123456");
        String json = objectMapper.writeValueAsString(paymentDto);
        Mockito.doNothing().when(paymentService).savePayment(Mockito.any());
        kafkaTemplate.send("00000", json);
        Thread.sleep(5000);
        Mockito.verify(paymentService, Mockito.times(0)).savePayment(Mockito.any());
    }
}