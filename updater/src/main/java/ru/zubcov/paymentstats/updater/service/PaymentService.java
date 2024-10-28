package ru.zubcov.paymentstats.updater.service;


import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;

public interface PaymentService {

    void savePayment(PaymentDto payment);
}
