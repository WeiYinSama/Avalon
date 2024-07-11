package net.avalon.rocketmq.api.rocketmqclient;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2023/5/19 - 22:35
 */
@SpringBootTest
public class DelayMessageTest {


    // 1  2  3   4   5    6    7    8    9    10   11   12   13   14    15    16    17 18
    // 1s 5s 10s 30s 1min 2min 3min 4min 5min 6min 7min 8min 9min 10min 20min 30min 1h 2h

    @Test
    void sendDelayMessage() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("Group1");
        producer.setNamesrvAddr("172.28.117.73:9876");
        // Launch producer
        producer.start();


        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("DelayTopic", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }

    @Test
    void receiveDelayMessage() throws MQClientException, InterruptedException {

        // 初始化consumer，并设置consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Group2");

        // 设置NameServer地址
        consumer.setNamesrvAddr("172.28.117.73:9876");
        //订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
//        consumer.subscribe("topic_001", "*");
        consumer.subscribe("DelayTopic", "*");

        //注册回调接口来处理从Broker中收到的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
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
}
