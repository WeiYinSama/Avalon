package net.avalon.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Weiyin
 * @Create: 2023/4/1 - 13:08
 */
@Configuration
public class RabbitConfig {

    private static final String EXCHANGE = "order.exchange";
    private static final String DELAY_QUEUE = "order.delay.queue";
    private static final String QUEUE = "order.queue";
    //30 min
    private static final Long TTL = 30 * 60 * 1000L;


    /**
     * 配置消息转换器
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }

    /**
     * 声明一个队列
     * <p>
     * 一旦创建，就不会被覆盖
     *
     * @return
     */
    @Bean
    public Queue myQueue() {
        return new Queue("0721");
    }


    /**
     * 创建一个延迟队列，一开始将消息发送到该队列
     * <p>
     * 1. 声明消息过期时间 x-message-ttl : 10*1000，即10s
     * 2. 声明消息过期后进入哪个交换机 x-dead-letter-exchange : order.exchange
     * 3. 声明消息过期后进入哪个队列 x-dead-letter-routing-key : order.queue
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", TTL);
        arguments.put("x-dead-letter-exchange", EXCHANGE);
        arguments.put("x-dead-letter-routing-key", QUEUE);

        return new Queue(DELAY_QUEUE, true, false, false, arguments);

    }

    /**
     * 创建一个普通队列，消费者要从该队列获取订单信息
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {

        return new Queue(QUEUE);
    }

    /**
     * 创建一个直连交换机
     *
     * @return
     */
    @Bean
    public Exchange orderExchange() {

        return new DirectExchange(EXCHANGE,true,false);
    }

    /**
     * 将延迟队列与交换机绑定
     *
     * @return
     */
    @Bean
    public Binding orderDelayBinding() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        //			@Nullable Map<String, Object> arguments
        return new Binding(DELAY_QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, DELAY_QUEUE, null);
    }

    /**
     * 将普通队列与交换机绑定
     *
     * @return
     */
    @Bean
    public Binding orderBinding() {

        return new Binding(QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, QUEUE, null);
    }
}
