package net.avalon.authority.dao;


import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.dao.bo.User;
import net.avalon.authority.mapper.generator.InviteCodeMapper;
import net.avalon.authority.mapper.generator.UserPoMapper;
import net.avalon.authority.mapper.generator.UserRolePoMapper;
import net.avalon.authority.mapper.generator.po.*;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.Common;
import net.avalon.generic.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2024/1/24 - 5:32
 */
@Repository
@Slf4j
public class UserDao {


    @Autowired
    private UserRolePoMapper userRolePoMapper;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserPoMapper mapper;

    @Autowired
    private InviteCodeMapper inviteCodeMapper;

    /**
     * 将用户的权限信息load到redis中，用于权限校验
     *
     * @param uid userID
     * @return void
     * <p>
     * createdBy: Ming Qiu 2020-11-02 11:44
     * modifiedBy: Ming Qiu 2020-11-03 12:31
     * 将获取用户Roleid的代码独立, 增加redis过期时间
     * Ming Qiu 2020-11-07 8:00
     * 集合里强制加“0”
     */
    public void loadPrivilegeIdsByUserId(Long uid) {

        //1.获得用户的所有角色
        List<Long> roleIds = this.getRoleIdsByUserId(uid);
        //2.定义 redis 中的 key 为 u_233
        String key = "u_" + uid;
        //3.创建一个保存 roleKey 的集合
        Set<String> roleKeys = new HashSet<>(roleIds.size());
        //4.遍历 roleIds,
        for (Long roleId : roleIds) {
            //设置 roleKey r_666
            String roleKey = "r_" + roleId;
            //将roleKey添加到 roleKeys
            roleKeys.add(roleKey);
            //redis 中 如果没有 r_666,那就加入
            if (!redisTemplate.hasKey(roleKey)) {
                roleDao.loadPrivsByRoleId(roleId);
            }
        }
        //把 roleKeys 对应的的所有值求并集，并将结果存到 u_id 中
        redisTemplate.opsForSet().unionAndStore(roleKeys, key);
//        redisTemplate.opsForSet().add(key, 0);
        // 角色的权限信息缓存多久呢？ 8h ~ 9h
        long randTimeout = Common.generateRandomTimeForRedis(8 * 60 * 60, 9 * 60 * 60);
        redisTemplate.expire(key, randTimeout, TimeUnit.SECONDS);
    }

    /**
     * 获得用户的角色id
     *
     * @param uid 用户id
     * @return 角色id列表
     * createdBy: Ming Qiu 2020/11/3 13:55
     */
    private List<Long> getRoleIdsByUserId(Long uid) {
        UserRolePoExample example = new UserRolePoExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo((byte) 1);

        List<UserRolePo> userRolePoList = userRolePoMapper.selectByExample(example);
        List<Long> retIds = new ArrayList<>(userRolePoList.size());
        for (UserRolePo po : userRolePoList) {
            retIds.add(po.getRoleId());
        }
        return retIds;
    }

    public Boolean checkUsernameAvailable(String username) {

        UserPoExample example = new UserPoExample();
        example.or().andUserNameEqualTo(username);

        List<UserPo> pos = mapper.selectByExample(example);

        return pos.size() == 0;
    }

    public Boolean checkEmailAvailable(String email) {

        UserPoExample example = new UserPoExample();
        example.or().andEmailEqualTo(email);

        List<UserPo> pos = mapper.selectByExample(example);

        return pos.size() == 0;
    }

    public void register(User bo) {
        UserPo po = bo.toPo();
        po.setName(UUID.randomUUID().toString().replaceAll("-", ""));
        po.setCreateTime(LocalDateTime.now());
        po.setState((byte) 1);
        // TODO 邀请码处理
        int i = inviteCodeMapper.useInviteCodeByCode(bo.getInviteCode());
        if (i == 0) {
            throw new AvalonException(AvalonStatus.FIELD_NOTVALID, "邀请码已失效");
        }
        try {
            mapper.insertSelective(po);
        } catch (DataAccessException e) {
            if (e.getMessage().contains("user.username_unique_index")) {
                throw new AvalonException(AvalonStatus.INSERT_FAIL, "账号已被使用，请重试");
            } else if (e.getMessage().contains("user.email_unique_index")) {
                throw new AvalonException(AvalonStatus.INSERT_FAIL, "邮箱已被使用，请重试");
            } else {
                throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
            }
        }
        // TODO 关联默认角色
        Long uid = po.getId();
        UserRolePo userRolePo = new UserRolePo();
        userRolePo.setUserId(uid);
        userRolePo.setRoleId(6L);
        userRolePo.setDeleted((byte) 1);
        userRolePo.setCreateTime(LocalDateTime.now());
        userRolePoMapper.insertSelective(userRolePo);
    }

    public User findByUsername(String userName) {
        UserPoExample example = new UserPoExample();
        example.or().andUserNameEqualTo(userName);
        try {
            List<UserPo> pos = mapper.selectByExample(example);
            if (pos.size() == 0) {
                throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST, "账号不存在");
            }
            return new User(pos.get(0));
        } catch (DataAccessException e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "数据库访问错误");
        }
    }

    public User getUserInfo(JwtUtil.User user) {

        Long uid = user.getId();

        UserPo po = mapper.selectByPrimaryKey(uid);

        return new User(po);
    }

    /**
     * 更新用户头像和昵称
     *
     * @param bo
     * @param user
     */
    public void updateUserInfo(User bo, JwtUtil.User user) {
        UserPo po = bo.toPo();
        po.setId(user.getId());
        mapper.updateByPrimaryKeySelective(po);
    }


    /**
     * auth002: 用户重置密码
     * @param vo 重置密码对象
     * @param ip 请求ip地址
     * @author 24320182203311 杨铭
     * Created at 2020/11/11 19:32
     */
//    public ReturnObject<Object> resetPassword(ResetPwdVo vo, String ip) {
//
//        //防止重复请求验证码
//        if(redisTemplate.hasKey("ip_"+ip))
//            return new ReturnObject<>(ResponseCode.AUTH_USER_FORBIDDEN);
//        else {
//            //1 min中内不能重复请求
//            redisTemplate.opsForValue().set("ip_"+ip,ip);
//            redisTemplate.expire("ip_" + ip, 60*1000, TimeUnit.MILLISECONDS);
//        }
//
//        //验证邮箱、手机号
//        UserPoExample userPoExample1 = new UserPoExample();
//        UserPoExample.Criteria criteria = userPoExample1.createCriteria();
//        criteria.andMobileEqualTo(AES.encrypt(vo.getMobile(),User.AESPASS));
//        List<UserPo> userPo1 = null;
//        try {
//            userPo1 = userMapper.selectByExample(userPoExample1);
//        }catch (Exception e) {
//            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,e.getMessage());
//        }
//        if(userPo1.isEmpty())
//            return new ReturnObject<>(ResponseCode.MOBILE_WRONG);
//        else if(!userPo1.get(0).getEmail().equals(AES.encrypt(vo.getEmail(), User.AESPASS)))
//            return new ReturnObject<>(ResponseCode.EMAIL_WRONG);
//
//
//        //随机生成验证码
//        String captcha = RandomCaptcha.getRandomString(6);
//        while(redisTemplate.hasKey(captcha))
//            captcha = RandomCaptcha.getRandomString(6);
//
//        String id = userPo1.get(0).getId().toString();
//        String key = "cp_" + captcha;
//        //key:验证码,value:id存入redis
//        redisTemplate.opsForValue().set(key,id);
//        //五分钟后过期
//        redisTemplate.expire("cp_" + captcha, 5*60*1000, TimeUnit.MILLISECONDS);
//
//
////        //发送邮件(请在配置文件application.properties填写密钥)
////        SimpleMailMessage msg = new SimpleMailMessage();
////        msg.setSubject("【oomall】密码重置通知");
////        msg.setSentDate(new Date());
////        msg.setText("您的验证码是：" + captcha + "，5分钟内有效。");
////        msg.setFrom("925882085@qq.com");
////        msg.setTo(vo.getEmail());
////        try {
////            mailSender.send(msg);
////        } catch (MailException e) {
////            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
////        }
//
//        return new ReturnObject<>(ResponseCode.OK);
//    }

    /**
     * auth002: 用户修改密码
     * @param modifyPwdVo 修改密码对象
     * @return Object
     * @author 24320182203311 杨铭
     * Created at 2020/11/11 19:32
     */
//    public ReturnObject<Object> modifyPassword(ModifyPwdVo modifyPwdVo) {
//
//
//        //通过验证码取出id
//        if(!redisTemplate.hasKey("cp_"+modifyPwdVo.getCaptcha()))
//            return new ReturnObject<>(ResponseCode.AUTH_INVALID_ACCOUNT);
//        String id= redisTemplate.opsForValue().get("cp_"+modifyPwdVo.getCaptcha()).toString();
//
//        UserPo userpo = null;
//        try {
//            userpo = userPoMapper.selectByPrimaryKey(Long.parseLong(id));
//        }catch (Exception e) {
//            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,e.getMessage());
//        }
//
//        //新密码与原密码相同
//        if(AES.decrypt(userpo.getPassword(), User.AESPASS).equals(modifyPwdVo.getNewPassword()))
//            return new ReturnObject<>(ResponseCode.PASSWORD_SAME);
//
//        //加密
//        UserPo userPo = new UserPo();
//        userPo.setPassword(AES.encrypt(modifyPwdVo.getNewPassword(),User.AESPASS));
//
//        //更新数据库
//        try {
//            userMapper.updateByPrimaryKeySelective(userPo);
//        }catch (Exception e) {
//            e.printStackTrace();
//            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR,e.getMessage());
//        }
//        return new ReturnObject<>(ResponseCode.OK);
//    }

    /* auth002 end*/


    /**
     * 创建user
     *
     * createdBy Li Zihan 243201822032227
     */
//    public ReturnObject addUser(NewUserPo po)
//    {
//        ReturnObject returnObject = null;
//        UserPo userPo = new UserPo();
//        userPo.setEmail(AES.encrypt(po.getEmail(), User.AESPASS));
//        userPo.setMobile(AES.encrypt(po.getMobile(), User.AESPASS));
//        userPo.setUserName(po.getUserName());
//        userPo.setAvatar(po.getAvatar());
//        userPo.setDepartId(po.getDepartId());
//        userPo.setOpenId(po.getOpenId());
//        userPo.setGmtCreate(LocalDateTime.now());
//        try{
//            returnObject = new ReturnObject<>(userPoMapper.insert(userPo));
//            logger.debug("success insert User: " + userPo.getId());
//        }
//        catch (DataAccessException e)
//        {
//            if (Objects.requireNonNull(e.getMessage()).contains("auth_user.user_name_uindex")) {
//                //若有重复名则修改失败
//                logger.debug("insertUser: have same user name = " + userPo.getName());
//                returnObject = new ReturnObject<>(ResponseCode.ROLE_REGISTERED, String.format("用户名重复：" + userPo.getName()));
//            } else {
//                logger.debug("sql exception : " + e.getMessage());
//                returnObject = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
//            }
//        }
//
//        catch (Exception e) {
//            // 其他Exception错误
//            logger.error("other exception : " + e.getMessage());
//            returnObject = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
//        }
//        return returnObject;
//    }
}
