package ru.zubcov.paymentstats.updater.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zubcov.paymentstats.updater.messaging.payment.PaymentDto;
import ru.zubcov.paymentstats.updater.model.Payment;
import ru.zubcov.paymentstats.updater.okvedApiClient.service.OkvedService;
import ru.zubcov.paymentstats.updater.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${bank.biks-values}")
    private Set<String> biks;
    private final ModelMapper modelMapper;
    private final OkvedService okvedService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void savePayment(PaymentDto paymentDto) {
        log.info("Saving payment to database");
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        if (paymentDto.getOkved() == null || paymentDto.getOkved().isBlank()) {
            log.info("OKVED is blank or null, check is transfer is intra bank");
            if (isIntraBankTransfer(payment)) {
                log.info("Payment is intra bank transfer");
                payment.setIntraBankTransfer(true);
            }
        } else {
            log.info("Get okved category from API");
            String okvedCategory = okvedService.getOkvedCategory(paymentDto.getOkved());
            log.info("Set category to payment");
            payment.setOkvedCategory(okvedCategory);
        }
        payment.setTimestamp(LocalDateTime.now());
        log.info("Save payment {}", payment);
        paymentRepository.save(payment);
    }

    private boolean isIntraBankTransfer(Payment payment) {
        return biks.contains(payment.getReceiveClientBankBIK());
    }
}
