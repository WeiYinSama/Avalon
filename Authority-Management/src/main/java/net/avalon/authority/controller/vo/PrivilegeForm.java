package net.avalon.authority.controller.vo;

import lombok.Data;
import net.avalon.authority.dao.bo.Privilege;

import java.time.LocalDateTime;

/**
 * @Author: Weiyin
 * @Create: 2024/1/28 - 10:27
 */
@Data
public class PrivilegeForm {

    private String name;
    private String url;
    private String requestType;


    public Privilege toBo() {
        Privilege bo = new Privilege();
        bo.setName(this.name);
        bo.setUrl(this.url);
        bo.setRequestType(this.requestType);
        return bo;
    }
}
