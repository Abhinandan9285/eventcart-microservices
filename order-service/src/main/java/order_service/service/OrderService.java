package order_service.service;

import order_service.dto.request.CreateOrderRequest;
import order_service.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
}