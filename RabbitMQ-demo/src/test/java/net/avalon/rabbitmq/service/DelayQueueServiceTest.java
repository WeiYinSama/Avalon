package net.avalon.rabbitmq.service;

import net.avalon.rabbitmq.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2023/4/5 - 17:14
 */
@SpringBootTest
class DelayQueueServiceTest {

    @Autowired
    private DelayQueueService service;

    @Test
    void sendOrder() {

        Order order = new Order();


        for (int i = 0; i < 10; i++) {
            order.setId(1L+i);
            service.sendOrder(order);
        }

    }
}