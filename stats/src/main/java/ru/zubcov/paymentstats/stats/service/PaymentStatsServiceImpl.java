package ru.zubcov.paymentstats.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.model.TransferStatsDto;
import ru.zubcov.paymentstats.stats.repository.CustomPaymentRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentStatsServiceImpl implements PaymentStatsService {

    private final CustomPaymentRepository paymentRepository;

    @Override
    public List<PaymentStatsDto> getPaymentStats(ClientRequestStatsDto request) {
        log.info("Get payments stats from client: {} ", request.getClientId());
        return paymentRepository.getClientPaymentStats(request);
    }

    @Override
    public TransferStatsDto getTransfersStats(ClientRequestStatsDto request) {
        log.info("Get transfer stats from client: {}", request.getClientId());
        return paymentRepository.getTransfersStats(request);
    }
}
