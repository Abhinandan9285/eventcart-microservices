package inventory_service.event.consumer;

import inventory_service.config.RabbitMQConfig;
import inventory_service.event.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Received Order Event : {}",event);

        log.info("Reserving inventory for product : {}",event.getProductCode());
    }
}