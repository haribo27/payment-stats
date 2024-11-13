package ru.zubcov.paymentstats.stats.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Column(name = "client_account", length = 20, nullable = false)
    private String clientAccount;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "okved_category ", length = 20)
    private String okvedCategory;
    @Column(name = "receive_account", length = 20, nullable = false)
    private String receiveAccount;
    @Column(name = "receive_client_bank_bik", nullable = false, length = 20)
    private String receiveClientBankBIK;
    @Column(name = "intra_bank_transfer", nullable = false)
    private Boolean intraBankTransfer;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
