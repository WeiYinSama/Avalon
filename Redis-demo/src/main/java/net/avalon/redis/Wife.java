package net.avalon.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2023/3/22 - 18:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wife {

    private Long id;
    private String name;
    private Integer age;
    private String info;
    private List<String> tags;
}
