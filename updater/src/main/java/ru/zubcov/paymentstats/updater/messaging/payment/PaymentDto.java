package ru.zubcov.paymentstats.updater.messaging.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    @JsonProperty("clientId")
    private long clientId;
    @JsonProperty("clientAccount")
    private String clientAccount;
    @JsonProperty("amount")
    private long amount;
    @JsonProperty("okved")
    private String okved;
    @JsonProperty("receiveAccount")
    private String receiveAccount;
}
