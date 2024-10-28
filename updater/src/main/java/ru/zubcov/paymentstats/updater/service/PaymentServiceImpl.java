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
        log.debug("Saving payment to database");
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        if (paymentDto.getOkved() == null || paymentDto.getOkved().isBlank()) {
            log.debug("OKVED is blank or null, check is transfer is intra bank");
            if (isIntraBankTransfer(payment)) {
                log.debug("Payment is intra bank transfer");
                payment.setIntraBankTransfer(true);
            }
        } else {
            log.debug("Get okved category from API");
            String okvedCategory = okvedService.getOkvedCategory(paymentDto.getOkved());
            log.debug("Set category to payment");
            payment.setOkvedCategory(okvedCategory);
        }
        log.debug("Save payment {}", payment);
        paymentRepository.save(payment);
    }

    private boolean isIntraBankTransfer(Payment payment) {
        return biks.contains(payment.getReceiveClientBankBIK());
    }
}
