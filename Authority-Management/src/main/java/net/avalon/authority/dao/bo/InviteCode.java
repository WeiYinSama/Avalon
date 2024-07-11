package net.avalon.authority.dao.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.avalon.authority.mapper.generator.po.InviteCodePo;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2024/1/31 - 18:44
 */
@Data
@NoArgsConstructor
public class InviteCode {

    private Long id;
    private String code;
    private Byte availableCount;
    private Long createBy;
    private LocalDateTime createTime;

    public InviteCode(InviteCodePo po){
        this.id = po.getId();
        this.code = po.getCode();
        this.availableCount = po.getAvailableCount();
        this.createBy = po.getCreateBy();
        this.createTime = po.getCreateTime();
    }
}
