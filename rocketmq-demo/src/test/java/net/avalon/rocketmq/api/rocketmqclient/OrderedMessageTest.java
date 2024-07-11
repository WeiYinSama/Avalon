package net.avalon.rocketmq.api.rocketmqclient;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
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

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2023/5/19 - 22:20
 */
@SpringBootTest
public class OrderedMessageTest {

    private static final String nameServerAddr = "172.21.35.84";

    private static final String topic = "TopicDemo2";

    /**
     * 两个生产者，向同一个队列发送顺序消息：A B C D E
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    void sendOrderedMessage() throws UnsupportedEncodingException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("p1");
            producer.setNamesrvAddr(nameServerAddr + ":9876");
            producer.start();

            String[] tags = new String[]{"A", "B", "C", "D", "E"};

            Thread t1 = new Thread(() -> {
                int i = 0;
                for (int j = 0; j < 5; j++) {
                    Message msg = null;
                    try {
                        msg = new Message(topic, null, (tags[j]).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("当前消息の内容：" + tags[j]);
                    SendResult sendResult = null;
                    try {
                        sendResult = producer.send(msg, new MessageQueueSelector() {
                            /**
                             * 以orderId作为分区分类标准，对所有队列个数取余，来对将相同orderId的消息发送到同一个队列中
                             *
                             * @param mqs 可以发送的队列
                             * @param msg 消息
                             * @param arg send接口中传入的Object对象 即 orderId
                             * @return 该消息需要发送到的队列
                             */
                            @Override
                            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
    //                            Integer orderId = (Integer) arg;
    //                            int index = orderId % mqs.size();
    //                            System.out.printf("该主题下队列大小为%d，当前消息使用的是队列%s\n", mqs.size(), index + 1);
                                int index = 0;
                                return mqs.get(index);
                            }
                        }, i);
                    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("%s%n", sendResult);
                }
            });

            Thread t2 = new Thread(() -> {
                int i = 1;
                for (int j = 0; j < 5; j++) {
                    Message msg = null;
                    try {
                        msg = new Message(topic, null, (tags[j]).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("当前消息の内容：" + tags[j]);
                    SendResult sendResult = null;
                    try {
                        sendResult = producer.send(msg, new MessageQueueSelector() {
                            /**
                             * 以orderId作为分区分类标准，对所有队列个数取余，来对将相同orderId的消息发送到同一个队列中
                             *
                             * @param mqs 可以发送的队列
                             * @param msg 消息
                             * @param arg send接口中传入的Object对象 即 orderId
                             * @return 该消息需要发送到的队列
                             */
                            @Override
                            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                //                            Integer orderId = (Integer) arg;
                                //                            int index = orderId % mqs.size();
                                //                            System.out.printf("该主题下队列大小为%d，当前消息使用的是队列%s\n", mqs.size(), index + 1);
                                int index = 0;
                                return mqs.get(index);
                            }
                        }, i);
                    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("%s%n", sendResult);
                }
            });

            t2.start();
            t1.start();
            TimeUnit.SECONDS.sleep(10);
            producer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void consumerOrderedMessage() throws MQClientException, InterruptedException {
        // 初始化consumer，并设置consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("c1");

        // 设置NameServer地址
        consumer.setNamesrvAddr(nameServerAddr + ":9876");
        //订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
//        consumer.subscribe("topic_001", "*");
        consumer.subscribe(topic, "*");

        //注册回调接口来处理从Broker中收到的消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

                for (MessageExt msg : msgs) {
                    System.out.println(context.getMessageQueue().getQueueId());
                    System.out.println("线程名称：【" + Thread.currentThread().getName() + "】" + "消费消息：" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动Consumer
        consumer.start();
        System.out.printf("Consumer Started.%n");
        TimeUnit.SECONDS.sleep(30);
    }
}
