package order_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.constant.OrderStatus;
import order_service.dto.request.CreateOrderRequest;
import order_service.dto.response.OrderResponse;
import order_service.entity.Order;
import order_service.event.payload.OrderConfirmedEvent;
import order_service.event.payload.OrderCreatedEvent;
import order_service.event.payload.OrderFailedEvent;
import order_service.event.producer.NotificationEventProducer;
import order_service.event.producer.OrderEventProducer;
import order_service.repository.OrderRepository;
import order_service.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements  OrderService {

    private final OrderRepository orderRepository;

    private final OrderEventProducer producer;
    private final NotificationEventProducer notificationEventProducer;

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

        log.info("Order created and published with Order: {}",event);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .status(savedOrder.getStatus().name())
                .build();
    }

    @Override
    public void markOrderConfirmed(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        notificationEventProducer.publishOrderConfirmedEvent(new OrderConfirmedEvent(orderId));
    }

    @Override
    public void markOrderFailed(UUID orderId, String reason) {

        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);

        notificationEventProducer.publishOrderFailedEvent(new OrderFailedEvent(orderId,reason));
    }
}