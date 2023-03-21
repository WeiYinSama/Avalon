package com.example.formcheck.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class User {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @Min(value = 0,message = "年龄不得小于0")
    private Integer age;
    private LocalDateTime createTime;

}
