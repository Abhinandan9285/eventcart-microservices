package payment_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment_service.dto.PaymentResponse;
import payment_service.entity.Payment;
import payment_service.repository.PaymentRepository;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;

    @GetMapping("/{orderId}")
    public PaymentResponse getPayment(@PathVariable("orderId") UUID orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId).orElse(new Payment());

        return new PaymentResponse(
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus()
        );
    }
}