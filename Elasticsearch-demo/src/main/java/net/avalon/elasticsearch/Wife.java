package net.avalon.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2023/3/21 - 10:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wife {

    private String name;
    private Integer age;
    private String info;
    private List<String> tags;
}
