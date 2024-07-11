package net.avalon.authority.controller.vo;

import lombok.Data;
import net.avalon.authority.dao.bo.User;

/**
 * @Author: Weiyin
 * @Create: 2024/2/1 - 1:30
 */
@Data
public class RegisterForm {

    private String username;

    private String password;

    private String email;

    private String inviteCode;

    public User toBo(){
        User bo = new User();
        bo.setUserName(this.username);
        bo.setPassword(this.password);
        bo.setEmail(this.email);

        bo.setInviteCode(this.inviteCode);
        return bo;
    }
}
