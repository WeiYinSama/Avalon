package net.avalon.alipay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.extern.slf4j.Slf4j;
import net.avalon.alipay.model.Order;
import net.avalon.alipay.service.AlipayService;
import net.avalon.common.util.AvalonException;
import net.avalon.common.util.AvalonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 8:20
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public String tradePrecreate(Order order) throws AvalonException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderSn());
        bizContent.put("total_amount", order.getTotalPrice());
        bizContent.put("subject", order.getOrderSn());
        request.setBizContent(bizContent.toString());

        AlipayTradePrecreateResponse response = null;

        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new AvalonException(AvalonStatus.ALIPAY_QRCODE_CREATE_FAIL);
        }

        if (response.isSuccess()) {
            log.info("订单 {} の 二维码：{}", response.getOutTradeNo(), response.getQrCode());
        } else {
            throw new AvalonException(AvalonStatus.ALIPAY_QRCODE_CREATE_FAIL);
        }
        return response.getQrCode();
    }

    @Override
    public String tradeQuery(Order order) throws AvalonException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderSn());
        request.setBizContent(bizContent.toString());

        AlipayTradeQueryResponse response = null;

        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new AvalonException(AvalonStatus.ALIPAY_TRADE_QUERY_FAIL);
        }

        if (response.isSuccess()) {
            log.info(String.format("订单号：%s\n订单金额：%s\n订单状态：%s", response.getOutTradeNo(), response.getTotalAmount(), response.getTradeStatus()));
        } else {
            log.info("调用失败");
        }
        return response.getTradeStatus();
    }

    @Override
    public void tradeCancel(Order order) throws AvalonException {
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderSn());
        request.setBizContent(bizContent.toString());

        AlipayTradeCancelResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new AvalonException(AvalonStatus.ALIPAY_TRADE_CANCEL_FAIL);
        }
        if (response.isSuccess()) {
            log.info("订单取消成功");
        } else {
            log.info("订单取消失败");
            tradeCancel(order);
        }
    }

    @Override
    public void tradeRefund(Order order) throws AvalonException {

        String tradeStatus = tradeQuery(order);
        if (!"TRADE_SUCCESS".equals(tradeStatus)) {
            throw new AvalonException(AvalonStatus.ALIPAY_TRADE_NOT_EXIST);
        }

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderSn());
        bizContent.put("refund_amount", order.getTotalPrice());
        request.setBizContent(bizContent.toString());

        AlipayTradeRefundResponse response = null;

        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new AvalonException(AvalonStatus.ALIPAY_TRADE_REFUND_FAIL);
        }

        if (response.isSuccess()) {
            log.info("退款成功");
        } else {
            tradeRefund(order);
        }
    }
}
