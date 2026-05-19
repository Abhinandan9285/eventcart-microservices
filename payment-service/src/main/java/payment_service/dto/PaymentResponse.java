package payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import payment_service.constant.PaymentStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private UUID orderId;

    private Double amount;

    private PaymentStatus status;
}