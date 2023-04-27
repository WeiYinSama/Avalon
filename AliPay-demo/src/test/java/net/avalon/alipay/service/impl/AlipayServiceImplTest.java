package net.avalon.alipay.service.impl;

import com.alipay.api.AlipayClient;
import lombok.extern.slf4j.Slf4j;
import net.avalon.alipay.model.Order;
import net.avalon.alipay.service.AlipayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 8:35
 */
@SpringBootTest
@Slf4j
class AlipayServiceImplTest {

    @Autowired
    private AlipayService alipayService;

    /**
     * 最佳实践
     * @throws InterruptedException
     */
    @Test
    void alipayTest() throws InterruptedException {
        Order order = new Order();
        order.setOrderSn("20230427093700035");
        order.setTotalPrice(new BigDecimal("88.88"));

        String qrCode = alipayService.tradePrecreate(order);
        // TODO 将 qrCode 转成图片供扫码支付

        String tradeStatus = null;
        //循环查询订单状态
        int count = 10;
        int last = count - 1;
        for (int i = 0; i < count; i++) {
            Thread.sleep(6 * 1000L);
            log.info("第{}次查询", i + 1);
            tradeStatus = alipayService.tradeQuery(order);

            if (i != last) {
                if (tradeStatus == null) continue;
                if (tradeStatus.equals("TRADE_SUCCESS")) break;
            } else {
                if (tradeStatus == null || !tradeStatus.equals("TRADE_SUCCESS")) {
                    alipayService.tradeCancel(order);
                }
            }

        }
    }

    @Test
    void tradePrecreate() {

        Order order = new Order();
        order.setOrderSn("20230427093700041");
        order.setTotalPrice(new BigDecimal("88.88"));

        String qrCode = alipayService.tradePrecreate(order);
        log.info("生成的二维码是：{}", qrCode);

    }

    /**
     * 订单查询
     */
    @Test
    void tradeQuery() {
        Order order = new Order();
        order.setOrderSn("20230427093700039");
        alipayService.tradeQuery(order);

    }

    @Test
    void tradeRefund() {
        Order order = new Order();
        order.setOrderSn("20230427093700017");
        order.setTotalPrice(new BigDecimal("88.88"));
        alipayService.tradeRefund(order);
    }

    @Test
    void tradeCancel() {

        Order order = new Order();
        order.setOrderSn("55555555");

        alipayService.tradeCancel(order);
    }
}