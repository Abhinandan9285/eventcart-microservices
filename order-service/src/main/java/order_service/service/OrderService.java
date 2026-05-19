package order_service.service;

import order_service.dto.OrderDetailsResponse;
import order_service.dto.request.CreateOrderRequest;
import order_service.dto.response.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    void markOrderConfirmed(UUID orderId);
    void markOrderFailed(UUID orderId, String reason);
    OrderDetailsResponse getOrderDetails(UUID orderId);
}