package ru.zubcov.paymentstats.stats.contoroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zubcov.paymentstats.stats.dto.ClientRequestStatsDto;
import ru.zubcov.paymentstats.stats.service.PaymentStatsService;
import ru.zubcov.paymentstats.stats.util.PaymentStatsBy;

@Controller
@RequestMapping("/payment/stats")
@RequiredArgsConstructor
public class PaymentStatsController {

    private final PaymentStatsService paymentStatsService;

    @GetMapping
    public ResponseEntity<?> getPaymentStats(@RequestBody @Valid ClientRequestStatsDto request,
                                             @RequestParam(value = "by", defaultValue = "PAYMENT") PaymentStatsBy param) {
        return switch (param) {
            case PAYMENT -> new ResponseEntity<>(paymentStatsService.getPaymentStats(request),
                    HttpStatus.OK);
            case TRANSFER -> new ResponseEntity<>(paymentStatsService.getTransfersStats(request),
                    HttpStatus.OK);
        };
    }
}
