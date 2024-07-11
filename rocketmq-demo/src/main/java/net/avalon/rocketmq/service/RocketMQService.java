package net.avalon.rocketmq.service;


import lombok.extern.slf4j.Slf4j;
import net.avalon.common.util.AvalonException;
import net.avalon.common.util.AvalonStatus;
import net.avalon.rocketmq.model.Order;
import net.avalon.rocketmq.util.JacksonUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @author Ming Qiu
 * @date Created in 2020/11/7 22:53
 **/
@Service
@Slf4j
public class RocketMQService implements InitializingBean, DisposableBean {

    // 1  2  3   4   5    6    7    8    9    10   11   12   13   14    15    16    17 18
    // 1s 5s 10s 30s 1min 2min 3min 4min 5min 6min 7min 8min 9min 10min 20min 30min 1h 2h

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    private static final DefaultMQProducer producer = new DefaultMQProducer("orderProducerGroup");

    @Override
    public void afterPropertiesSet() throws AvalonException {
        // 设置NameServer地址
        producer.setNamesrvAddr("172.29.244.234:9876");
        try {
            // 启动producer
            producer.start();
        } catch (MQClientException e) {
            throw new AvalonException(AvalonStatus.ROCKETMQ_CLIENT_EXCEPTION);
        }
    }


    @Override
    public void destroy() {
        producer.shutdown();
    }

    /**
     * 发送同步消息
     * Topic 为硬编码
     * <p>
     * 1. 将 order 封装成 message
     * 2. 发送消息
     * 3. 查看是否发送成功
     *
     * @param order 订单
     * @throws AvalonException
     */
    public void sendOrder(Order order) throws AvalonException {

        try {
            //1. 将 order 封装成 message
            String json = JacksonUtil.toJson(order);
            Message message = new Message();
            message.setTopic("topic-order");
            message.setBody(json.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //2. 发送消息
            SendResult sendResult = producer.send(message);
            //3. 查看是否发送成功
            SendStatus status = sendResult.getSendStatus();
            if (!status.equals(SendStatus.SEND_OK)) {
                throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
            }
            log.info("订单发送成功。订单编号：{}，megId：{}", order.getOrderId(), sendResult.getMsgId());
        } catch (Exception e) {
            log.error("订单发送失败，编号：{}", order.getOrderId());
            throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
        }

    }


    /**
     * 发送单向消息
     * <p>
     * 发送方只负责发送消息，不等待服务端返回响应且没有回调函数触发，即只发送请求不等待应答。
     * 此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param order
     * @throws AvalonException
     */
    public void sendOrderOneway(Order order) throws AvalonException {

        try {
            //1. 将 order 封装成 message
            String json = JacksonUtil.toJson(order);
            Message message = new Message();
            message.setTopic("topic-order");
            message.setBody(json.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //2. 发送消息
            producer.sendOneway(message);

            log.info("订单已发送。订单编号：{}", order.getOrderId());
        } catch (Exception e) {
            log.error("订单发送失败，编号：{}", order.getOrderId());
            throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
        }
    }

    /**
     * 发送延时消息
     *
     * @param order
     * @param delayTimeLevel  1  2  3   4   5    6    7    8    9    10   11   12   13   14    15    16    17 18
     *                        1s 5s 10s 30s 1min 2min 3min 4min 5min 6min 7min 8min 9min 10min 20min 30min 1h 2h
     * @throws AvalonException
     */
    public void sendOrderDelay(Order order,Integer delayTimeLevel) throws AvalonException {

        try {
            //1. 将 order 封装成 message
            String json = JacksonUtil.toJson(order);
            Message message = new Message();
            message.setTopic("topic-order");
            message.setBody(json.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //2. 设置延时等级
            message.setDelayTimeLevel(delayTimeLevel);
            //3. 发送消息
            SendResult sendResult = producer.send(message);
            //4. 查看是否发送成功
            SendStatus status = sendResult.getSendStatus();
            if (!status.equals(SendStatus.SEND_OK)) {
                throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
            }
            log.info("订单发送成功。订单编号：{}，megId：{}", order.getOrderId(), sendResult.getMsgId());
        } catch (Exception e) {
            log.error("订单发送失败，编号：{}", order.getOrderId());
            throw new AvalonException(AvalonStatus.MESSAGE_SEND_FAIL);
        }
    }


}
