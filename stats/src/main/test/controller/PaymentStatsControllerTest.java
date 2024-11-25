package controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.zubcov.paymentstats.stats.contoroller.PaymentStatsController;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.model.TransferStatsDto;
import ru.zubcov.paymentstats.stats.service.PaymentStatsService;
import ru.zubcov.paymentstats.stats.util.PaymentStatsBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentStatsControllerTest {

    @Mock
    private PaymentStatsService paymentStatsService;

    @InjectMocks
    private PaymentStatsController paymentStatsController;

    public PaymentStatsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPaymentStatsByPayment() {
        ClientRequestStatsDto requestDto = new ClientRequestStatsDto(1L, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        PaymentStatsDto paymentStatsDto = new PaymentStatsDto("H", new BigDecimal(10000));
        when(paymentStatsService.getPaymentStats(requestDto)).thenReturn(List.of(paymentStatsDto));

        ResponseEntity<?> response = paymentStatsController.getPaymentStats(requestDto, PaymentStatsBy.PAYMENT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(paymentStatsDto));
        verify(paymentStatsService, times(1)).getPaymentStats(requestDto);
        verifyNoMoreInteractions(paymentStatsService);
    }

    @Test
    void testGetPaymentStatsByTransfer() {
        // Arrange
        ClientRequestStatsDto requestDto = new ClientRequestStatsDto(1L, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        TransferStatsDto transferStatsDto = new TransferStatsDto(new BigDecimal(10000));
        when(paymentStatsService.getTransfersStats(requestDto)).thenReturn(transferStatsDto);

        ResponseEntity<?> response = paymentStatsController.getPaymentStats(requestDto, PaymentStatsBy.TRANSFER);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(transferStatsDto);
        verify(paymentStatsService, times(1)).getTransfersStats(requestDto);
        verifyNoMoreInteractions(paymentStatsService);
    }
}
