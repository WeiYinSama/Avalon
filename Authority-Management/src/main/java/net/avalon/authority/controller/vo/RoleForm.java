package net.avalon.authority.controller.vo;

import lombok.Data;
import net.avalon.authority.dao.bo.Role;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 17:18
 */
@Data
public class RoleForm {

    private String name;
    private String descr;

    public Role toBo(){
        Role bo = new Role();
        bo.setName(this.name);
        bo.setDescr(this.descr);
        return bo;
    }
}
