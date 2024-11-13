package ru.zubcov.paymentstats.stats.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.model.*;

import java.util.List;

@Repository
public class CustomPaymentRepositoryImpl implements CustomPaymentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentStatsDto> getClientPaymentStats(ClientRequestStatsDto request) {
        QPayment payment = QPayment.payment;
        JPAQuery<PaymentStatsDto> query = new JPAQuery<>(entityManager);
        return query
                .select(new QPaymentStatsDto(
                        payment.okvedCategory,
                        payment.amount.sum()
                ))
                .from(payment)
                .where(
                        payment.clientId.eq(request.getClientId())
                                .and(payment.intraBankTransfer.eq(false))
                )
                .where(payment.okvedCategory.isNotNull())
                .where(request.getStartDate() != null ? payment.timestamp.goe(request.getStartDate()) : null)
                .where(request.getEndDate() != null ? payment.timestamp.loe(request.getEndDate()) : null)
                .groupBy(payment.okvedCategory)
                .fetch();
    }

    @Override
    public TransferStatsDto getTransfersStats(ClientRequestStatsDto request) {
        QPayment payment = QPayment.payment;
        JPAQuery<PaymentStatsDto> query = new JPAQuery<>(entityManager);
        return query
                .select(new QTransferStatsDto(
                        payment.amount.sum()
                ))
                .from(payment)
                .where(payment.okvedCategory.isNull())
                .where(payment.clientId.eq(request.getClientId())
                        .and(payment.intraBankTransfer.eq(false))
                ).fetchOne();
    }
}
