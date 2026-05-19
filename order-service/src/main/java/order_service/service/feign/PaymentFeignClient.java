package order_service.service.feign;

import order_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "payment-service")
public interface PaymentFeignClient {

    @GetMapping("/payments/{orderId}")
    PaymentResponse getPayment(@PathVariable("orderId") UUID orderId);
}