package net.avalon.rocketmq.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2023/5/21 - 17:29
 */
@Data
public class Order {

    private Long orderId;

    // 收货人
    private String receiver;
    // 收货地址
    private String address;

    // 联系方式
    private String phone;
    private double totalPrice;
    private byte status;

    // 下单人
    private Long createId;
    private LocalDateTime createTime;
}
