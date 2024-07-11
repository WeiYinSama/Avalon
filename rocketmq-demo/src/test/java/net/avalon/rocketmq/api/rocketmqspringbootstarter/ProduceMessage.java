package net.avalon.rocketmq.api.rocketmqspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import net.avalon.common.util.AvalonException;
import net.avalon.common.util.AvalonStatus;
import net.avalon.rocketmq.model.Order;
import net.avalon.rocketmq.util.JacksonUtil;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 普通消息
 * @Author: Weiyin
 * @Create: 2023/5/21 - 17:12
 */
@SpringBootTest
@Slf4j
public class ProduceMessage {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    private static String topic = "order";

    @Test
    void syncSend() {
        Order order = new Order();
        order.setOrderId(2L);
        order.setReceiver("lacia");
        order.setCreateTime(LocalDateTime.now());

        String json = JacksonUtil.toJson(order);
        Message<String> message = MessageBuilder.withPayload(json).build();
        SendResult result = rocketMQTemplate.syncSend(topic, message);

        if(!result.getSendStatus().equals(SendStatus.SEND_OK)){
            log.error("发送失败");
            throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
        }
        log.info("发送成功");

    }

    @Test
    void asyncSend() throws InterruptedException {

        Order order = new Order();
        order.setOrderId(4L);
        order.setReceiver("lacia");
        order.setCreateTime(LocalDateTime.now());

        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(JacksonUtil.toJson(order)).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("发送失败");
            }
        });

        TimeUnit.SECONDS.sleep(5L);
    }


    @Test
    void sendOneWay() {
        Order order = new Order();
        order.setOrderId(5L);
        order.setReceiver("lacia");
        order.setCreateTime(LocalDateTime.now());

        rocketMQTemplate.sendOneWay(topic,MessageBuilder.withPayload(JacksonUtil.toJson(order)).build());
        log.info("消息已发送");

    }
}
