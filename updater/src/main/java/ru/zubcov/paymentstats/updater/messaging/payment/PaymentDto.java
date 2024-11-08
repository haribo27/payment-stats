package ru.zubcov.paymentstats.updater.messaging.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {

    @JsonProperty("clientId")
    private final long clientId;
    @JsonProperty("clientAccount")
    private final String clientAccount;
    @JsonProperty("amount")
    private final long amount;
    @JsonProperty("okved")
    private final String okved;
    @JsonProperty("receiveAccount")
    private final String receiveAccount;
}
