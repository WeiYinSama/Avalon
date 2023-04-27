package net.avalon.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 响应体
 *
 * @Author: Weiyin
 * @Create: 2023/2/26 - 16:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {

    private int code;
    private String message;
    private Object data;
}
