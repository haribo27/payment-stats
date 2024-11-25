package ru.zubcov.paymentstats.updater.payment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.zubcov.paymentstats.updater.BaseTestContainerClass;
import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;
import ru.zubcov.paymentstats.updater.model.Payment;
import ru.zubcov.paymentstats.updater.okvedApiClient.service.OkvedService;
import ru.zubcov.paymentstats.updater.repository.PaymentRepository;
import ru.zubcov.paymentstats.updater.service.PaymentService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
@Transactional
class PaymentServiceTests extends BaseTestContainerClass {

    @MockBean
    private OkvedService okvedService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void testSavePayment() {
        PaymentDto paymentDto = new PaymentDto(1, "100", new BigDecimal(500), "51",
                "200", "123456");
        Payment payment = new Payment();
        payment.setClientId(1L);
        payment.setClientAccount("100");
        payment.setAmount(new BigDecimal(500));
        payment.setReceiveAccount("200");
        payment.setReceiveClientBankBIK("123456");
        when(okvedService.getOkvedCategory(anyString())).thenReturn("SomeCategory");
        when(modelMapper.map(paymentDto, Payment.class)).thenReturn(payment);

        paymentService.savePayment(paymentDto);

        Optional<Payment> savedPayment = paymentRepository.findById(1L);
        assertTrue(savedPayment.isPresent(), "Payment should be saved in the database");
        Assertions.assertEquals(paymentDto.getAmount(), savedPayment.get().getAmount());
        Assertions.assertEquals("SomeCategory", savedPayment.get().getOkvedCategory());
        Assertions.assertEquals(paymentDto.getClientAccount(), savedPayment.get().getClientAccount());
        Assertions.assertEquals(payment.getClientId(), savedPayment.get().getClientId());
        Assertions.assertEquals(paymentDto.getReceiveAccount(), savedPayment.get().getReceiveAccount());
    }

    @Test
    public void testSavePaymentWithClientAccountIsNull() {
        PaymentDto paymentDto = new PaymentDto(1, null,
                new BigDecimal(500), "51", "200", "123456");
        Payment payment = new Payment();
        payment.setClientId(1L);
        payment.setClientAccount(null);
        payment.setAmount(new BigDecimal(500));
        payment.setReceiveAccount("200");
        payment.setReceiveClientBankBIK("123456");
        when(okvedService.getOkvedCategory(anyString())).thenReturn("SomeCategory");
        when(modelMapper.map(paymentDto, Payment.class)).thenReturn(payment);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> paymentService.savePayment(paymentDto));
    }

    @Test
    public void testSavePaymentWithReceiveAccountIsNull() {
        PaymentDto paymentDto = new PaymentDto(1, "1234",
                new BigDecimal(500), "51", null, "123456");
        Payment payment = new Payment();
        payment.setClientId(1L);
        payment.setClientAccount(null);
        payment.setAmount(new BigDecimal(500));
        payment.setReceiveAccount(null);
        payment.setReceiveClientBankBIK("123456");
        when(okvedService.getOkvedCategory(anyString())).thenReturn("SomeCategory");
        when(modelMapper.map(paymentDto, Payment.class)).thenReturn(payment);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> paymentService.savePayment(paymentDto));
    }

    @Test
    public void testSavePaymentWithOkvedMoreThan20Symbols() {
        PaymentDto paymentDto = new PaymentDto(1, "1234",
                new BigDecimal(500), "51", "sdfttds", "123456");
        Payment payment = new Payment();
        payment.setClientId(1L);
        payment.setClientAccount("1234");
        payment.setAmount(new BigDecimal(500));
        payment.setReceiveAccount("sdfttds");
        payment.setReceiveClientBankBIK("123456");
        when(okvedService.getOkvedCategory(anyString())).thenReturn("SomeCategory00000000000000000000000000000");
        when(modelMapper.map(paymentDto, Payment.class)).thenReturn(payment);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> paymentService.savePayment(paymentDto));
    }

    @Test
    public void testThatTransferIsIntraBankTransferAndOkvedNull() {
        PaymentDto paymentDto = new PaymentDto(2, "1234",
                new BigDecimal(500), null, "5678", "868688910");
        Payment payment = new Payment();
        payment.setClientId(2L);
        payment.setClientAccount("1234");
        payment.setAmount(new BigDecimal(500));
        payment.setReceiveAccount("sdfttds");
        payment.setReceiveClientBankBIK("868688910");
        when(okvedService.getOkvedCategory(anyString())).thenReturn("SomeCategory");
        when(modelMapper.map(paymentDto, Payment.class)).thenReturn(payment);

        paymentService.savePayment(paymentDto);

        Optional<Payment> savedPayment = paymentRepository.findById(2L);
        assertTrue(savedPayment.isPresent(), "Payment should be saved in the database");
        assertTrue(savedPayment.get().isIntraBankTransfer());
    }
}
