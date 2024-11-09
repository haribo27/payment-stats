package ru.zubcov.paymentstats.stats.service;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zubcov.paymentstats.stats.dto.*;
import ru.zubcov.paymentstats.stats.model.QPayment;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentStatsServiceImpl implements PaymentStatsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentStatsDto> getPaymentStats(ClientRequestStatsDto request) {
        log.info("Get payments stats from client: {} ", request.getClientId());
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
        log.info("Get transfer stats from client: {}", request.getClientId());
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
