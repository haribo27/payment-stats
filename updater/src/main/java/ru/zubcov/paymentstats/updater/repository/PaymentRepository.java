package ru.zubcov.paymentstats.updater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zubcov.paymentstats.updater.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
