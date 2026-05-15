package inventory_service.event.producer;

import inventory_service.config.RabbitMQConfig;
import inventory_service.event.payload.InventoryFailedEvent;
import inventory_service.event.payload.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishSuccessEvent(InventoryReservedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INVENTORY_SUCCESS_QUEUE, event);
    }

    public void publishFailureEvent(InventoryFailedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INVENTORY_FAILED_QUEUE, event);
    }
}