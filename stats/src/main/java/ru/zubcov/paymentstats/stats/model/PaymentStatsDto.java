package ru.zubcov.paymentstats.stats.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public final class PaymentStatsDto {

    private final String category;
    private final BigDecimal sumOfPayments;

    @QueryProjection
    public PaymentStatsDto(String category, BigDecimal sumOfPayments) {
        this.category = category;
        this.sumOfPayments = sumOfPayments;
    }
}
