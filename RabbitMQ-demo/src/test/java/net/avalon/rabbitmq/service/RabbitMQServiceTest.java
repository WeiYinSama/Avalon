package net.avalon.rabbitmq.service;

import net.avalon.rabbitmq.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @Author: Weiyin
 * @Create: 2023/4/1 - 16:55
 */
@SpringBootTest
class RabbitMQServiceTest {

    @Autowired
    private RabbitMQService service;

    @Autowired
    private AmqpAdmin admin;

    /**
     * 声明一个队列
     */
    @Test
    void createQueue() {
        admin.declareQueue(new Queue("0721"));
    }

    @Test
    void sendOrder() {
        Order order = new Order();
        for (int i = 0; i < 10; i++) {
            order.setId(1L + i);
            service.sendOrder(order);
        }
    }

    @Test
    void receiveOrder() {
        Order order = service.receiveOrder();
    }
}