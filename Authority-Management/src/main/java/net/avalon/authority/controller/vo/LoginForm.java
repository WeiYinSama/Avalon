package net.avalon.authority.controller.vo;

import lombok.Data;
import net.avalon.authority.dao.bo.User;

/**
 * @Author: Weiyin
 * @Create: 2024/2/5 - 17:17
 */
@Data
public class LoginForm {

    private String username;
    private String password;


    public User toBo(){
        User bo = new User();
        bo.setUserName(this.username);
        bo.setPassword(this.password);
        return bo;
    }
}
