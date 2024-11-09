package ru.zubcov.paymentstats.stats.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.zubcov.paymentstats.stats.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>, QuerydslPredicateExecutor<Payment> {
}
