package net.avalon.authority.dao.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalon.authority.mapper.generator.po.PrivilegePo;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2024/1/24 - 4:57
 */
@Data
@NoArgsConstructor
public class Privilege {

    private Long id;
    private String name;
    private String url;
    private String requestType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /**
     * privilege的key
     */
    private String key;

    /**
     * 构造函数
     *
     * @param po 用PO构造
     */
    public Privilege(PrivilegePo po) {
        this.id = po.getId();
        this.name = po.getName();
        this.url = po.getUrl();
        this.requestType = po.getRequestType();
        this.createTime = po.getCreateTime();
        this.updateTime = po.getUpdateTime();

        this.key = String.format("%s-%s",po.getUrl(),po.getRequestType());
    }

    public PrivilegePo toPo() {

        PrivilegePo po = new PrivilegePo();
        po.setName(this.name);
        po.setUrl(this.url);
        po.setRequestType(this.requestType);
        return po;
    }
}
