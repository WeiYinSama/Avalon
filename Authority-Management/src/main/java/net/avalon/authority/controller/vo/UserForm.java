package net.avalon.authority.controller.vo;

import lombok.Data;
import net.avalon.authority.dao.bo.User;

/**
 * @Author: Weiyin
 * @Create: 2024/2/21 - 5:52
 */
@Data
public class UserForm {

    private String name;
    private String avatar;



    public User toBo(){
        User bo = new User();
        bo.setName(this.name);
        bo.setAvatar(this.avatar);

        return bo;
    }
}
