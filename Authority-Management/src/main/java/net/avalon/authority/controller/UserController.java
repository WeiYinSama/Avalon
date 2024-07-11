package net.avalon.authority.controller;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.aop.annotation.Audit;
import net.avalon.authority.aop.annotation.OpenAPI;
import net.avalon.authority.controller.vo.LoginForm;
import net.avalon.authority.controller.vo.RegisterForm;
import net.avalon.authority.controller.vo.UserForm;
import net.avalon.authority.dao.UserDao;
import net.avalon.authority.dao.bo.User;
import net.avalon.authority.service.UserService;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.Common;
import net.avalon.generic.core.util.JwtUtil;
import net.avalon.generic.core.util.R;
import net.avalon.generic.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Weiyin
 * @Create: 2024/1/22 - 19:57
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromMail.addr}")
    private String mailFrom;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService service;

    @Autowired
    private UserDao userDao;

    /**
     * 校验账号是否可用
     * @param username 登陆账号
     * @return
     */
    @GetMapping("/username/available")
    @OpenAPI
    public R checkUsernameAvailable(@RequestParam String username) {

        Boolean valid = service.checkUsernameAvailable(username);

        return ResponseUtil.ok(valid);
    }

    /**
     * 加载用户权限信息
     * @param id
     * @return
     */
    @GetMapping("/loadpriv/{id}")
    @OpenAPI
    public R loadPrivilegeIdsByUserId(@PathVariable Long id) {

        userDao.loadPrivilegeIdsByUserId(id);
        return ResponseUtil.ok();
    }

    /**
     * 校验邮箱是否可用
     * @param email
     * @return
     */
    @GetMapping("/email/available")
    @OpenAPI
    public R checkEmailAvailable(@RequestParam String email) {

        Boolean valid = service.checkEmailAvailable(email);

        return ResponseUtil.ok(valid);
    }

    /**
     * 校验邮箱验证码是否正确
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/verifyCode/check")
    @OpenAPI
    public R checkVerifyCode(@RequestParam String email, @RequestParam String code) {

        String cacheCode = redisTemplate.opsForValue().get(email).toString();

        return ResponseUtil.ok(cacheCode.equals(code));
    }

    /**
     * 发送验证码到指定邮箱
     * @param email
     * @return
     */
    @GetMapping("/email/validate")
    @OpenAPI
    public R emailCheck(@RequestParam String email) {

        try {
            // 创建一个邮件消息
            MimeMessage message = javaMailSender.createMimeMessage();
            // 创建 MimeMessageHelper
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 发件人邮箱和名称
            helper.setFrom(mailFrom, "Lacia");
            // 收件人邮箱
            helper.setTo(email);
            // 邮件标题
            helper.setSubject("验证码");
            // 邮件正文，第二个参数表示是否是HTML正文
            String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
            helper.setText("<h1 style=\"width: 600px; height: 600px; margin: 0 auto; text-align: center;line-height: 600px;\">" + code + "</h1>", true);
            // 发送
            log.info("开始发送消息");
            javaMailSender.send(message);
            log.info("发送完毕");

            redisTemplate.opsForValue().set(email, code);
            redisTemplate.expire(email, 3 * 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AvalonException(AvalonStatus.INTERNAL_SERVER_ERR, "邮件发送失败");
        }
        return ResponseUtil.ok();
    }

    /**
     * 注册账号
     * @param form
     * @return
     */
    @PostMapping("/register")
    @OpenAPI
    public R register(@RequestBody RegisterForm form) {

        service.register(form.toBo());

        return ResponseUtil.created();
    }

    /**
     * 登录
     * @param form
     * @return
     */
    @PostMapping("/login")
    @OpenAPI
    public R login(@RequestBody LoginForm form) {

        String token = service.login(form.toBo());

        return ResponseUtil.ok(token);
    }


    /**
     * 获取用户基本信息
     *
     * @param user
     * @return
     */
    @GetMapping("/user")
    @Audit
    public R getUserInfo(JwtUtil.User user) {

        log.info("User:{}", user);
        User ret = service.getUserInfo(user);
        return ResponseUtil.ok(ret);
    }

    /**
     * 更新用户基本信息
     * @param form
     * @param user
     * @return
     */
    @PostMapping("/user/update")
    @Audit
    public R updateUserInfo(@RequestBody UserForm form, JwtUtil.User user) {

        service.updateUserInfo(form.toBo(), user);
        return ResponseUtil.created();
    }
}
