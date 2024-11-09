package ru.zubcov.paymentstats.stats.service;

import jakarta.validation.Valid;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.dto.PaymentStatsDto;
import ru.zubcov.paymentstats.stats.dto.TransferStatsDto;

import java.util.List;

public interface PaymentStatsService {

    List<PaymentStatsDto> getPaymentStats(ClientRequestStatsDto request);

    TransferStatsDto getTransfersStats(@Valid ClientRequestStatsDto request);
}
