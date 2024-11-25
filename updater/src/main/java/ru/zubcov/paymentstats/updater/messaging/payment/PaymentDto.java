package ru.zubcov.paymentstats.updater.messaging.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public final class PaymentDto {

    @JsonProperty("clientId")
    private final long clientId;
    @JsonProperty("clientAccount")
    private final String clientAccount;
    @JsonProperty("amount")
    private final BigDecimal amount;
    @JsonProperty("okved")
    private final String okved;
    @JsonProperty("receiveAccount")
    private final String receiveAccount;
    @JsonProperty("receiveClientBankBIK")
    private String receiveClientBankBIK;
}
