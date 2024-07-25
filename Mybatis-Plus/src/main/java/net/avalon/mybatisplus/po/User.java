package net.avalon.mybatisplus.po;

import lombok.Data;

/**
 * @Author: Heda
 * @Create: 2024/7/25
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}