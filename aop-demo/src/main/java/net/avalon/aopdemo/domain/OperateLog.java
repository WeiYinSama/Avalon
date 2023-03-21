package net.avalon.aopdemo.domain;

import lombok.Data;

/**
 * @author weiyin
 * @time 2022/12/3 - 21:57
 */
@Data
public class OperateLog {
    private Long orderId;
    private String desc;
    private String result;
}
