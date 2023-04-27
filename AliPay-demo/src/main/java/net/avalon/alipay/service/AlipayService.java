package net.avalon.alipay.service;

import net.avalon.alipay.model.Order;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 8:19
 */
public interface AlipayService {

    /**
     * 预下单
     * <p>
     * 再次下单，会返回一个新的二维码
     * @param order
     * @return 订单二维码（有效时间 2 小时）以字符串的格式返回，开发者需要自己使用工具根据内容生成二维码图片。
     */
    String tradePrecreate(Order order);

    /**
     * 查询交易状态
     *
     * @param order
     * @return tradeStatus：
     * 1. WAIT_BUYER_PAY：交易创建，等待买家付款。
     * 2. TRADE_SUCCESS：交易支付成功
     * 3. TRADE_CLOSED：未付款交易超时关闭，或支付完成后全额退款。
     * 4. TRADE_FINISHED：交易结束，不可退款
     * 5. null：用户尚未扫码 或订单id不存在
     */
    String tradeQuery(Order order);

    /**
     * 取消订单
     * @param order
     */
    void tradeCancel(Order order);

    /**
     * 退款
     * @param order
     */
    void tradeRefund(Order order);
}
