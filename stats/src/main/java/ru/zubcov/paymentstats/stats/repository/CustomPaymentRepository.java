package ru.zubcov.paymentstats.stats.repository;

import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.model.TransferStatsDto;

import java.util.List;

public interface CustomPaymentRepository {
    List<PaymentStatsDto> getClientPaymentStats(ClientRequestStatsDto requestStatsDto);

    TransferStatsDto getTransfersStats(ClientRequestStatsDto request);
}
