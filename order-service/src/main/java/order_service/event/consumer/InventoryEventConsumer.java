package order_service.event.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.config.RabbitMQConfig;
import order_service.event.payload.InventoryFailedEvent;
import order_service.event.payload.InventoryReservedEvent;
import order_service.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_SUCCESS_QUEUE)
    public void consumeInventoryReservedEvent(InventoryReservedEvent event) {

        log.info("Inventory Reserved Event Received : {}", event);

        orderService.markOrderConfirmed(event.getOrderId());
    }

    @RabbitListener(
            queues = RabbitMQConfig.INVENTORY_FAILED_QUEUE
    )
    public void consumeInventoryFailedEvent(InventoryFailedEvent event) {

        log.info("Inventory Failed Event Received : {}", event);

        orderService.markOrderFailed(event.getOrderId(), event.getReason());
    }
}