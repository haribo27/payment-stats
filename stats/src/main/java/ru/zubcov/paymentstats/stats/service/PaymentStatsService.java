package ru.zubcov.paymentstats.stats.service;

import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.model.TransferStatsDto;

import java.util.List;

public interface PaymentStatsService {

    List<PaymentStatsDto> getPaymentStats(ClientRequestStatsDto request);

    TransferStatsDto getTransfersStats(ClientRequestStatsDto request);
}
