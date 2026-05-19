package order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import order_service.constant.OrderStatus;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponse {

    private UUID orderId;

    private String productCode;

    private Integer quantity;

    private OrderStatus status;

    private InventoryResponse inventory;

    private PaymentResponse payment;
}