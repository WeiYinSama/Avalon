package net.avalon.rocketmq.api.rocketmqclient;

import net.avalon.rocketmq.model.Order;
import net.avalon.rocketmq.util.JacksonUtil;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2023/5/19 - 19:59
 */
@SpringBootTest
public class RocketmqClientConsumerTest {

    private String consumerGroupName = "pay-group";
    private String topic = "topic-order";
    private String nameServer = "172.29.244.234:9876";


    @Test
    void push() throws MQClientException, InterruptedException {
        // 初始化consumer，并设置consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroupName);

        // 设置NameServer地址
        consumer.setNamesrvAddr(nameServer);
        //订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
//        consumer.subscribe("order-pay-topic", "*");
        consumer.subscribe(topic, "*");
        //注册回调接口来处理从Broker中收到的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                System.out.printf("%s %n", Thread.currentThread().getName());
                MessageExt messageExt = msgs.get(0);
                String json = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                Order order = JacksonUtil.toObj(json, Order.class);
                System.out.printf("%s - 接收到的订单：%s\n", Thread.currentThread().getName(), order);

                //如果消费成功，返回消息处理结果
                // 返回消息消费状态，ConsumeConcurrentlyStatus.CONSUME_SUCCESS为消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动Consumer
        consumer.start();
        System.out.printf("Consumer Started.%n");
        TimeUnit.SECONDS.sleep(30);
    }

    public static volatile boolean running = true;

    @Test
    void pull() throws MQClientException {

        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer(consumerGroupName);
        litePullConsumer.setNamesrvAddr(nameServer);
        litePullConsumer.subscribe(topic, "*");
        litePullConsumer.setPullBatchSize(20);
        litePullConsumer.start();
        try {
            while (running) {
                List<MessageExt> messageExts = litePullConsumer.poll();
                System.out.printf("%s%n", messageExts);
            }
        } finally {
            litePullConsumer.shutdown();
        }

    }
}
