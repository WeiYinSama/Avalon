package net.avalon.rocketmq.service;

import net.avalon.rocketmq.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2023/5/26 - 0:50
 */
@SpringBootTest
class RocketMQServiceTest {


    @Autowired
    private RocketMQService rocketMQService;

    @Test
    void sendOrder() {
        Order order = new Order();
        order.setTotalPrice(233);
        order.setCreateTime(LocalDateTime.now());
        order.setAddress("Avalon");
        order.setPhone("15364514749");

        for (int i = 0; i < 10; i++) {
            order.setOrderId((long) i);
            rocketMQService.sendOrder(order);
        }

    }

    @Test
    void sendOrderOneway() {
        Order order = new Order();
        order.setOrderId(5L);
        order.setTotalPrice(233);
        order.setCreateTime(LocalDateTime.now());
        order.setAddress("Avalon");
        order.setPhone("15364514749");

        rocketMQService.sendOrderOneway(order);
    }

    @Test
    void sendOrderDelay() {
        Order order = new Order();
        order.setOrderId(6L);
        order.setTotalPrice(233);
        order.setCreateTime(LocalDateTime.now());
        order.setAddress("Avalon");
        order.setPhone("15364514749");

        rocketMQService.sendOrderDelay(order,3);
    }
}