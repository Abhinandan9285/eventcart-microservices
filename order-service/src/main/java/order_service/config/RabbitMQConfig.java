package order_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.routing.key";

    public static final String ORDER_DLQ = "order.dlq";

    public static final String INVENTORY_SUCCESS_QUEUE = "inventory.success.queue";
    public static final String INVENTORY_FAILED_QUEUE = "inventory.failed.queue";

    public static final String INVENTORY_SUCCESS_ROUTING_KEY = "inventory.success.routing.key";
    public static final String INVENTORY_FAILED_ROUTING_KEY = "inventory.failed.routing.key";

    public static final String PAYMENT_SUCCESS_QUEUE = "payment.success.queue";
    public static final String PAYMENT_FAILED_QUEUE = "payment.failed.queue";

    public static final String PAYMENT_SUCCESS_ROUTING_KEY = "payment.success.routing.key";
    public static final String PAYMENT_FAILED_ROUTING_KEY = "payment.failed.routing.key";

    public static final String ORDER_CONFIRMED_ROUTING_KEY = "order.confirmed.routing.key";
    public static final String ORDER_FAILED_ROUTING_KEY = "order.failed.routing.key";
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }



    @Bean
    public Queue orderQueue() {

        HashMap<String, Object> args = new HashMap<>();

        /*
         * Failed messages go to DLQ
         */
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", ORDER_DLQ);

        return new Queue(
                ORDER_QUEUE,
                true,
                false,
                false,
                args
        );
    }


    @Bean
    public Queue deadLetterQueue() {
        return new Queue(ORDER_DLQ);
    }



    @Bean
    public Queue inventorySuccessQueue() {
        return new Queue(INVENTORY_SUCCESS_QUEUE);
    }


    @Bean
    public Queue inventoryFailedQueue() {
        return new Queue(INVENTORY_FAILED_QUEUE);
    }

    @Bean
    public Queue paymentSuccessQueue() {
        return new Queue(PAYMENT_SUCCESS_QUEUE);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(PAYMENT_FAILED_QUEUE);
    }


    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_ROUTING_KEY);
    }


    @Bean
    public Binding inventorySuccessBinding() {

        return BindingBuilder
                .bind(inventorySuccessQueue())
                .to(orderExchange())
                .with(INVENTORY_SUCCESS_ROUTING_KEY);
    }

    @Bean
    public Binding inventoryFailedBinding() {

        return BindingBuilder
                .bind(inventoryFailedQueue())
                .to(orderExchange())
                .with(INVENTORY_FAILED_ROUTING_KEY);
    }

    @Bean
    public Binding paymentSuccessBinding() {

        return BindingBuilder
                .bind(paymentSuccessQueue())
                .to(orderExchange())
                .with(PAYMENT_SUCCESS_ROUTING_KEY);
    }

    @Bean
    public Binding paymentFailedBinding() {

        return BindingBuilder
                .bind(paymentFailedQueue())
                .to(orderExchange())
                .with(PAYMENT_FAILED_ROUTING_KEY);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}