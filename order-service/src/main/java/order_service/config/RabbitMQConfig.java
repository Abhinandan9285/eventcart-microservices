package order_service.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE = "order.queue";

    public static final String ORDER_DLQ = "order.dlq";

    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_ROUTING_KEY = "order.routing.key";


    /*
     * =========================================
     * INVENTORY RESULT QUEUES
     * =========================================
     */

    public static final String INVENTORY_SUCCESS_QUEUE = "inventory.success.queue";

    public static final String INVENTORY_FAILED_QUEUE = "inventory.failed.queue";


    /*
     * =========================================
     * ORDER EXCHANGE
     * =========================================
     */

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }


    /*
     * =========================================
     * MAIN ORDER QUEUE
     * =========================================
     */

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


    /*
     * =========================================
     * DEAD LETTER QUEUE
     * =========================================
     */

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(ORDER_DLQ);
    }


    /*
     * =========================================
     * BIND ORDER QUEUE TO EXCHANGE
     * =========================================
     */

    @Bean
    public Binding orderBinding() {

        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_ROUTING_KEY);
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
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}