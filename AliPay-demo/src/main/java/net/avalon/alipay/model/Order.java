package net.avalon.alipay.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Weiyin
 * @Create: 2023/4/27 - 8:22
 */
@Data
public class Order {

    private String orderSn;
    private BigDecimal totalPrice;
}
