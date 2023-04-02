package net.avalon.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import net.avalon.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Weiyin
 * @Create: 2023/4/1 - 16:37
 */
@Service
@Slf4j
public class RabbitMQService {

    private static final String QUEUE_NAME = "0721";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param order
     */
    public void sendOrder(Order order) {

        rabbitTemplate.convertAndSend(QUEUE_NAME, order);
        log.info("已发送订单：{}", order);
    }

    /**
     * 拉取模式
     *
     * @return
     */
    public Order receiveOrder() {

        Order order = (Order) rabbitTemplate.receiveAndConvert(QUEUE_NAME);
        log.info("收到订单：{}", order);
        return order;
    }

    /**
     * 推送模式
     *
     * @param order
     */
    @RabbitListener(queues = QUEUE_NAME)
    public void receiveOrder(Order order) {

        log.info("收到订单：{}", order);
    }

}
