package order_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import order_service.constant.OrderStatus;
import order_service.dto.request.CreateOrderRequest;
import order_service.dto.response.OrderResponse;
import order_service.entity.Order;
import order_service.event.payload.OrderCreatedEvent;
import order_service.event.producer.OrderEventProducer;
import order_service.repository.OrderRepository;
import order_service.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements  OrderService {

    private final OrderRepository orderRepository;

    private final OrderEventProducer producer;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .productCode(request.getProductCode())
                .quantity(request.getQuantity())
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event =
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getProductCode(),
                        savedOrder.getQuantity());

        producer.publishOrderCreatedEvent(event);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .status(savedOrder.getStatus().name())
                .build();
    }
}