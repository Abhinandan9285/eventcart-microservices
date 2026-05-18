package order_service.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.config.RabbitMQConfig;
import order_service.event.payload.OrderConfirmedEvent;
import order_service.event.payload.OrderFailedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderConfirmedEvent(OrderConfirmedEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_CONFIRMED_ROUTING_KEY,
                event
        );
    }

    public void publishOrderFailedEvent(OrderFailedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_FAILED_ROUTING_KEY,
                event
        );
    }
}