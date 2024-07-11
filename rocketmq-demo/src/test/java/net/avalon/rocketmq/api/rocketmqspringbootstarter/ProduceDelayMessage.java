package net.avalon.rocketmq.api.rocketmqspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import net.avalon.rocketmq.model.Order;
import net.avalon.rocketmq.util.JacksonUtil;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2023/5/28 - 15:57
 */
@SpringBootTest
@Slf4j
public class ProduceDelayMessage {

    // 1  2  3   4   5    6    7    8    9    10   11   12   13   14    15    16    17 18
    // 1s 5s 10s 30s 1min 2min 3min 4min 5min 6min 7min 8min 9min 10min 20min 30min 1h 2h

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void name() throws InterruptedException {
        Order order = new Order();
        order.setOrderId(12L);
        order.setReceiver("lacia");
        order.setCreateTime(LocalDateTime.now());

        rocketMQTemplate.asyncSend("order", MessageBuilder.withPayload(JacksonUtil.toJson(order)).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功，发送时间：{}",LocalDateTime.now());
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("发送失败");
            }
        },3L*1000,3);

        TimeUnit.SECONDS.sleep(3L);
    }
}
