package order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import order_service.constant.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String orderId;

    private Double amount;

    private PaymentStatus status;
}