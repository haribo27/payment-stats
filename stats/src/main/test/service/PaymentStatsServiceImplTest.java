package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.model.TransferStatsDto;
import ru.zubcov.paymentstats.stats.repository.CustomPaymentRepository;
import ru.zubcov.paymentstats.stats.service.PaymentStatsServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentStatsServiceImplTest {

    @Mock
    private CustomPaymentRepository paymentRepository;

    @InjectMocks
    private PaymentStatsServiceImpl paymentStatsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPaymentStats() {
        ClientRequestStatsDto requestDto = new ClientRequestStatsDto(1L, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        
        List<PaymentStatsDto> mockResponse = List.of(new PaymentStatsDto("H",new BigDecimal(5000)),
                new PaymentStatsDto("G", new BigDecimal(10000)));
        when(paymentRepository.getClientPaymentStats(requestDto)).thenReturn(mockResponse);

        List<PaymentStatsDto> result = paymentStatsService.getPaymentStats(requestDto);

        assertThat(result).isEqualTo(mockResponse);
        verify(paymentRepository, times(1)).getClientPaymentStats(requestDto);
        verifyNoMoreInteractions(paymentRepository);
    }

    @Test
    void testGetTransfersStats() {
        // Arrange
        ClientRequestStatsDto requestDto = new ClientRequestStatsDto(2L,LocalDateTime.now(),
                LocalDateTime.now().plusDays(2));
        
        TransferStatsDto mockResponse = new TransferStatsDto(new BigDecimal(200000));
        when(paymentRepository.getTransfersStats(requestDto)).thenReturn(mockResponse);

        TransferStatsDto result = paymentStatsService.getTransfersStats(requestDto);

        assertThat(result).isEqualTo(mockResponse);
        verify(paymentRepository, times(1)).getTransfersStats(requestDto);
        verifyNoMoreInteractions(paymentRepository);
    }
}
