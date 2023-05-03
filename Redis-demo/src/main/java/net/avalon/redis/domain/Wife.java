package net.avalon.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2023/3/22 - 18:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wife implements Serializable{

    private Long id;
    private String name;
    private Integer age;
    private String info;
    private LocalDateTime createTime;
    private List<String> tags;
}
