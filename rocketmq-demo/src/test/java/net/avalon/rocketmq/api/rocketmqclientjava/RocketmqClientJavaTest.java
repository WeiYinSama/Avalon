package net.avalon.rocketmq.api.rocketmqclientjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Weiyin
 * @Create: 2023/5/17 - 14:41
 */
@SpringBootTest
@Slf4j
class RocketmqClientJavaTest {

//    @Test
//    void produce() throws ClientException {
//        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
//        String endpoint = "172.25.80.198:8081";
//        // 消息发送的目标Topic名称，需要提前创建。
//        String topic = "TestTopic";
//
//        ClientConfiguration configuration = ClientConfiguration.newBuilder().setEndpoints(endpoint).build();
//
//        ClientServiceProvider provider = ClientServiceProvider.loadService();
//
//
//        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
//        Producer producer = provider.newProducerBuilder()
//                .setTopics(topic)
//                .setClientConfiguration(configuration)
//                .build();
//        // 普通消息发送。
//        Message message = provider.newMessageBuilder()
//                .setTopic(topic)
//                // 设置消息索引键，可根据关键字精确查找某条消息。
//                .setKeys("messageKey")
//                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
//                .setTag("messageTag")
//                // 消息体。
//                .setBody("messageBody".getBytes())
//                .build();
//
//        try {
//            // 发送消息，需要关注发送结果，并捕获失败等异常。
//            SendReceipt sendReceipt = producer.send(message);
//            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
//        } catch (ClientException e) {
//            log.error("Failed to send message", e);
//        }
//        // producer.close();
//    }

//    @Test
//    void consumer() throws ClientException, InterruptedException {
//        final ClientServiceProvider provider = ClientServiceProvider.loadService();
//        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
//        String endpoints = "localhost:8081";
//        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
//                .setEndpoints(endpoints)
//                .build();
//        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
//        String tag = "*";
//        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
//        // 为消费者指定所属的消费者分组，Group需要提前创建。
//        String consumerGroup = "YourConsumerGroup";
//        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
//        String topic = "TestTopic";
//        // 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
//        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
//                .setClientConfiguration(clientConfiguration)
//                // 设置消费者分组。
//                .setConsumerGroup(consumerGroup)
//                // 设置预绑定的订阅关系。
//                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
//                // 设置消费监听器。
//                .setMessageListener(messageView -> {
//                    // 处理消息并返回消费结果。
//                    log.info("Consume message successfully, messageId={}", messageView.getMessageId());
//                    return ConsumeResult.SUCCESS;
//                })
//                .build();
//        Thread.sleep(Long.MAX_VALUE);
//        // 如果不需要再使用 PushConsumer，可关闭该实例。
//        // pushConsumer.close();
//    }
}