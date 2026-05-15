package order_service.event.producer;

import lombok.RequiredArgsConstructor;
import order_service.config.RabbitMQConfig;
import order_service.event.payload.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_QUEUE,
                event
        );
    }
}