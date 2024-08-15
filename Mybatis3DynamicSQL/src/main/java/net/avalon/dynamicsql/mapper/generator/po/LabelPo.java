package net.avalon.dynamicsql.mapper.generator.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LabelPo {
    private Long id;

    private String name;

    private Long comicId;

    private Long userId;

    private Byte deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}