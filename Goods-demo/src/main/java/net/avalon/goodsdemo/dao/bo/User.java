package net.avalon.goodsdemo.dao.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/21 - 23:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
}
