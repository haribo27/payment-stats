package ru.zubcov.paymentstats.updater;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {

    private long id;
    private long clientId;
    private String clientAccount;
    private long amount;
    private String orved;
    private String receiveAccount;
}
