package net.avalon.rocketmq.api.rocketmqclient;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2023/5/19 - 18:53
 */
@SpringBootTest
public class RocketmqClientProducerTest {


    @Test
    void name() {

    }

    /**
     * 发送同步消息
     *
     * @throws Exception
     */
    @Test
    void sendSyncMessage() throws Exception {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("groupA"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("172.28.117.73:9876");  //（2）
        // 启动producer
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
            Message msg = new Message("topic_001" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );   //（3）
            // 利用producer进行发送，并同步等待发送结果
            SendResult sendResult = producer.send(msg);   //（4）
            System.out.printf("%s%n", sendResult);
        }
        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }

    /**
     * 发送异步消息
     *
     * @throws MQClientException
     * @throws InterruptedException
     */
    @Test
    void sendAsyncMessage() throws MQClientException, InterruptedException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("groupB");
        // 设置NameServer地址
        producer.setNamesrvAddr("172.28.117.73:9876");
        // 启动producer
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
                Message msg = new Message("topic_001",
                        "TagB",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                // 异步发送消息, 发送结果通过callback返回给客户端
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index,
                                sendResult.getMsgId());
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                        countDownLatch.countDown();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        }
        //异步发送，如果要求可靠传输，必须要等回调接口返回明确结果后才能结束逻辑，否则立即关闭Producer可能导致部分消息尚未传输成功
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }


    /**
     * 单向发送消息
     * <p>
     * 不在乎是否发送成功
     * 应用场景：日志
     *
     * @throws MQClientException
     * @throws UnsupportedEncodingException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    void sendOneWayMessage() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("groupC");
        // 设置NameServer地址
        producer.setNamesrvAddr("172.28.117.73:9876");
        // 启动producer
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
            Message msg = new Message("topic_001" /* Topic */,
                    "TagC" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 由于在oneway方式发送消息时没有请求应答处理，如果出现消息发送失败，则会因为没有重试而导致数据丢失。若数据不可丢，建议选用可靠同步或可靠异步发送方式。
            producer.sendOneway(msg);
            System.out.printf("消息%d已发送%n", i);
        }
        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }
}
