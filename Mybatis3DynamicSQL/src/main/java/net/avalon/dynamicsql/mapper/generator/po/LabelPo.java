package net.avalon.dynamicsql.mapper.generator.po;

import lombok.Data;

import java.time.LocalDateTime;
import javax.annotation.Generated;

@Data
public class LabelPo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.comic_id")
    private Long comicId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.deleted")
    private Byte deleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.update_time")
    private LocalDateTime updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1304684+08:00", comments="Source field: label.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.comic_id")
    public Long getComicId() {
        return comicId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.comic_id")
    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.user_id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.deleted")
    public Byte getDeleted() {
        return deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.deleted")
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1314701+08:00", comments="Source field: label.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}