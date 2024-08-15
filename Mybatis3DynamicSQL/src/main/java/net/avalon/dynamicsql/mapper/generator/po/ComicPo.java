package net.avalon.dynamicsql.mapper.generator.po;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ComicPo {
    private Long id;

    private String name;

    private String cover;

    private String author;

    private String nameCn;

    private String translator;

    private String introduction;

    private Long view;

    private String region;

    private Byte adult;

    private Byte type;

    private Long categoryId;

    private Long userId;

    private Byte deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}