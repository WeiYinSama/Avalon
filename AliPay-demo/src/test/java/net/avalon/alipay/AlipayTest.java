package net.avalon.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 7:52
 */
@SpringBootTest
@Slf4j
public class AlipayTest {

    @Autowired
    private AlipayClient alipayClient;


    @Test
    public void precreate() throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "20230427000000002");
        bizContent.put("total_amount", 0.01);
        bizContent.put("subject", "测试商品2");
        request.setBizContent(bizContent.toString());

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        //https://qr.alipay.com/bax09283lutdtoylqcxv0004
        //https://qr.alipay.com/bax044737c90wmvci4wg00db
        if (response.isSuccess()) {
            log.info("调用成功");
            log.info("二维码：{}", response.getQrCode());
        } else {
            System.out.println("调用失败");
        }
    }

    @Test
    public void tradeQuery() throws AlipayApiException {

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "1");
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println(response.getTradeStatus());
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    @Test
    void tradeRefund() throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "20230427093700007");
        bizContent.put("refund_amount", 88.88);
        request.setBizContent(bizContent.toString());

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
