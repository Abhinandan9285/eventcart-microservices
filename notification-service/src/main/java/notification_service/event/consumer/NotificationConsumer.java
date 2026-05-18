package notification_service.event.consumer;

import lombok.extern.slf4j.Slf4j;
import notification_service.config.RabbitMQConfig;
import notification_service.event.payload.OrderConfirmedEvent;
import notification_service.event.payload.OrderFailedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {
    @RabbitListener(queues = RabbitMQConfig.ORDER_CONFIRMED_NOTIFICATION_QUEUE)
    public void consumeOrderConfirmedEvent(OrderConfirmedEvent event) {
        log.info("EMAIL SENT : Order Confirmed : {}", event.getOrderId());
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_FAILED_NOTIFICATION_QUEUE)
    public void consumeOrderFailedEvent(OrderFailedEvent event) {
        log.info("EMAIL SENT : Order Failed : {} Reason : {}",
                event.getOrderId(),
                event.getReason()
        );
    }
}