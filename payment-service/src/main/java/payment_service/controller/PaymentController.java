package payment_service.controller;

import common_lib.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment_service.dto.ContactInfoDto;
import payment_service.entity.Payment;
import payment_service.repository.PaymentRepository;
import payment_service.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final ContactInfoDto contactInfoDto;

    @GetMapping("/{orderId}")
    public PaymentResponse getPayment(@PathVariable("orderId") UUID orderId) {
        return  paymentService.getPayment(orderId);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> contactInfo(){
        return ResponseEntity.ok(contactInfoDto);
    }
}