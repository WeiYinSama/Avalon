package net.avalon.goodsdemo.dao.bo;

import lombok.Data;
import net.avalon.goodsdemo.mapper.generator.po.OnSalePo;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/21 - 23:23
 */
@Data
public class OnSale {
    private Long id;

    private Long price;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer quantity;

    /**
     * 创建者
     */
    private User creator;

    /**
     * 修改者
     */
    private User modifier;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 从数据库获得数据 构造 OnSale 对象
     * @param po
     */
    public OnSale(OnSalePo po) {
        assert null!=po;
        id = po.getId();
        price = po.getPrice();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        quantity = po.getQuantity();
        creator = new User(po.getCreatorId(), po.getCreatorName());
        modifier = new User(po.getModifierId(), po.getModifierName());
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    public OnSalePo createPo(){
        OnSalePo po = new OnSalePo();
        po.setPrice(price);
        po.setBeginTime(beginTime);
        po.setEndTime(endTime);
        po.setQuantity(quantity);
        return po;
    }
}
