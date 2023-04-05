package net.avalon.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import net.avalon.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2023/4/5 - 17:09
 */
@Service
@Slf4j
public class DelayQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 监听订单队列消息
     * @param order
     */
    @RabbitListener(queues = "order.queue")
    public void receiveOrder(Order order) {

        log.info("收到订单：{} - {}", order,LocalDateTime.now());
    }

    /**
     * 向延迟队列发送消息
     * @param order
     */
    public void sendOrder(Order order) {

        rabbitTemplate.convertAndSend("order.exchange","order.delay.queue", order);
        log.info("已发送订单：{} - {}", order, LocalDateTime.now());
    }
}
