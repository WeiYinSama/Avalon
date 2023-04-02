package net.avalon.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: Weiyin
 * @Create: 2023/4/1 - 13:08
 */
@Configuration
public class RabbitConfig {

    /**
     * 配置消息转换器
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    /**
//     * 声明一个队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue myQueue() {
//        return new Queue("0721");
//    }
}
