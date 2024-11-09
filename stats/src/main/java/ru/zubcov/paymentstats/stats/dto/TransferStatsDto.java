package ru.zubcov.paymentstats.stats.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public final class TransferStatsDto {

    private final BigDecimal amount;

    @QueryProjection
    public TransferStatsDto(BigDecimal amount) {
        this.amount = amount;
    }
}
