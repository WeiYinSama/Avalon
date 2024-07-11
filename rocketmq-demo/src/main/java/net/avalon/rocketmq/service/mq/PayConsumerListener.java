package net.avalon.rocketmq.service.mq;

import lombok.extern.slf4j.Slf4j;
import net.avalon.rocketmq.model.Order;
import net.avalon.rocketmq.util.JacksonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 消息消费者
 *
 * @author Ming Qiu
 * @date Created in 2020/11/7 22:47
 **/
@Service
@Slf4j
@RocketMQMessageListener(topic = "order", selectorExpression = "*", consumerGroup = "producer-group", messageModel = MessageModel.CLUSTERING, maxReconsumeTimes = -1)
public class PayConsumerListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        Order order = JacksonUtil.toObj(message, Order.class);

        log.info("onMessage: got message orderId =" + order.getOrderId() + " time = " + LocalDateTime.now());
    }

}
