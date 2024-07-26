package net.avalon.dynamicsql.mapper.generator.po;

import lombok.Data;

import java.time.LocalDateTime;
import javax.annotation.Generated;

@Data
public class ComicPo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1157367+08:00", comments="Source field: comic.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.11636+08:00", comments="Source field: comic.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.cover")
    private String cover;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.author")
    private String author;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.name_cn")
    private String nameCn;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.translator")
    private String translator;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.introduction")
    private String introduction;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.view")
    private Long view;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.region")
    private String region;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.adult")
    private Byte adult;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.type")
    private Byte type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.category_id")
    private Long categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.deleted")
    private Byte deleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.create_time")
    private LocalDateTime createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1182696+08:00", comments="Source field: comic.update_time")
    private LocalDateTime updateTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.11636+08:00", comments="Source field: comic.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.11636+08:00", comments="Source field: comic.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.11636+08:00", comments="Source field: comic.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.cover")
    public String getCover() {
        return cover;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.cover")
    public void setCover(String cover) {
        this.cover = cover;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.author")
    public String getAuthor() {
        return author;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.name_cn")
    public String getNameCn() {
        return nameCn;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.name_cn")
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.translator")
    public String getTranslator() {
        return translator;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1169901+08:00", comments="Source field: comic.translator")
    public void setTranslator(String translator) {
        this.translator = translator;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.introduction")
    public String getIntroduction() {
        return introduction;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.introduction")
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.view")
    public Long getView() {
        return view;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.view")
    public void setView(Long view) {
        this.view = view;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.region")
    public String getRegion() {
        return region;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.region")
    public void setRegion(String region) {
        this.region = region;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.adult")
    public Byte getAdult() {
        return adult;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.adult")
    public void setAdult(Byte adult) {
        this.adult = adult;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.type")
    public Byte getType() {
        return type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.type")
    public void setType(Byte type) {
        this.type = type;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.category_id")
    public Long getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.category_id")
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.user_id")
    public Long getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.user_id")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.deleted")
    public Byte getDeleted() {
        return deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.deleted")
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.117495+08:00", comments="Source field: comic.create_time")
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1182696+08:00", comments="Source field: comic.create_time")
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1182696+08:00", comments="Source field: comic.update_time")
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2024-07-26T20:41:12.1182696+08:00", comments="Source field: comic.update_time")
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}