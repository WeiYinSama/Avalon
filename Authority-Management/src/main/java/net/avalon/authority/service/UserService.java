package net.avalon.authority.service;

import net.avalon.authority.dao.UserDao;
import net.avalon.authority.dao.bo.User;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 8:32
 */
@Service
public class UserService {

    @Autowired
    private UserDao dao;


    /**
     * 检查账号是否可用
     * @param username
     * @return
     */
    public Boolean checkUsernameAvailable(String username) {
        return dao.checkUsernameAvailable(username);
    }

    public Boolean checkEmailAvailable(String email) {
        return dao.checkEmailAvailable(email);
    }

    @Transactional(rollbackFor = AvalonException.class)
    public void register(User bo) {
        dao.register(bo);
    }


    /**
     *
     * 1. 根据 username 从数据库拿到用户信息
     * 2. 比较密码
     * 3. 加载用户权限
     * 4. 返回用户token
     * @param bo
     * @return
     */
    public String login(User bo) {

        User user = dao.findByUsername(bo.getUserName());
        if(!bo.getPassword().equals(user.getPassword())){
            throw new AvalonException(AvalonStatus.AUTH_INVALID_ACCOUNT,"密码错误");
        }
        dao.loadPrivilegeIdsByUserId(user.getId());

        return JwtUtil.createToken(new JwtUtil.User(user.getId(), user.getName()), 8 * 24 * 60 * 60);
    }

    public User getUserInfo(JwtUtil.User user) {
        return dao.getUserInfo(user);
    }

    public void updateUserInfo(User bo, JwtUtil.User user) {
        dao.updateUserInfo(bo,user);
    }
}
