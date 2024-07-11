package net.avalon.authority.dao.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalon.authority.mapper.generator.po.RolePo;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2024/1/24 - 5:09
 */
@Data
@NoArgsConstructor
public class Role {

    private Long id;
    private String name;
    private String descr;
    private Long creatorId;
    private Byte deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public RolePo toPo() {
        RolePo po = new RolePo();
        po.setName(this.name);
        po.setDescr(this.descr);

        return po;
    }

    public Role(RolePo po){
        this.id = po.getId();
        this.name = po.getName();
        this.descr = po.getDescr();
        this.creatorId = po.getCreatorId();
        this.createTime = po.getCreateTime();
        this.updateTime = po.getUpdateTime();
    }
}
