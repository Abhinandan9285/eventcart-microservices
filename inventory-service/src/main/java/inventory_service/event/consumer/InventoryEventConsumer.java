package inventory_service.event.consumer;

import inventory_service.config.RabbitMQConfig;
import inventory_service.event.payload.OrderCreatedEvent;
import inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryService inventoryService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Received Order Event : {}", event);

        inventoryService.reserveInventory(event);
    }
}