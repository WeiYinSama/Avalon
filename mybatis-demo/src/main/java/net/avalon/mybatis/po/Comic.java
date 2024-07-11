package net.avalon.mybatis.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2023/11/15 - 22:36
 */
@Data
public class Comic {

    private Long id;
    private String name;
    private String cover;
    private String author;
    private String originalName;
    private String translator;
    private String introduction;
    private Long view;
    private Byte status;
    private String region;
    private Byte adult;
    private Long categoryId;
    private Byte deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
