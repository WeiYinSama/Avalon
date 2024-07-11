package net.avalon.authority.aop.aspect;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.avalon.authority.aop.annotation.OpenAPI;
import net.avalon.authority.dao.UserDao;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 9:21
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserDao userDao;

    /**
     * * gateway001 权限过滤器
     * * 1. 检查JWT是否合法,以及是否过期，如果过期则需要在response的头里换发新JWT，如果不过期将旧的JWT在response的头中返回
     * * 2. 判断用户的shopid是否与路径上的shopid一致（0可以不做这一检查）
     * * 3. 在redis中判断用户是否有权限访问url,如果不在redis中需要通过dubbo接口load用户权限
     * * 4. 需要以dubbo接口访问privilegeservice
     */
    @Before("net.avalon.authority.aop.CommonPointCuts.auth()")
    public void doAccessCheck(JoinPoint jp) {

        // 带有 @OpenAPI 注解的方法可直接通过，不用校验
        // 获取方法签名
        MethodSignature ms = (MethodSignature)jp.getSignature();
        Method method = ms.getMethod();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(OpenAPI.class)){
                return;
            }
        }

        log.info("进入身份校验AOP...");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();


        // 1. 拿到用户的token
        String token = request.getHeader("authorization");
        log.info("请求携带的token：{}",token);
        // 2. token 校验
        JwtUtil.TokenInfo tokenInfo = JwtUtil.verifyToken(token);
        if(tokenInfo == null){
            log.warn("token 校验异常，请重新登录");
            throw new AvalonException(AvalonStatus.AUTH_INVALID_JWT,"token 校验异常，请重新登录");
        }

        // 验证通过
        log.info("校验通过，欢迎使用系统，尊敬的 {}",tokenInfo.getUser().getName());
        response.setHeader("authorization",token);
        response.setHeader("Access-Control-Expose-Headers","authorization");
        // 3. 拿到请求的路径
        String path = request.getRequestURI();


        log.info("请求路径为：{}",path);
        // 4. 拿到请求方法
        String requestMethod = request.getMethod();
        log.info("请求方法为：{}",requestMethod);

        Pattern pattern = Pattern.compile("/[1-9][0-9]*");
        String newPath = pattern.matcher(path).replaceAll("/{id}");

        String hashkey = newPath + "-" + requestMethod;

        log.info("key：{}",hashkey);

        // 拿到权限id
        String pid = null;
        try{
            pid = redisTemplate.opsForHash().get("Priv", hashkey).toString();
            log.info("pid：{}",pid);
        }catch (Exception e){
            throw new AvalonException(AvalonStatus.RESOURCE_ID_NOTEXIST,"Page Not Found");
        }

        // 拿到用户id
        Long uid = tokenInfo.getUser().getId();
        String key = "u_" + uid;

        // 检查缓存中是否有用户权限信息，没有则加载
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            userDao.loadPrivilegeIdsByUserId(uid);
        }

        // 如果无权限，返回403
        if(!Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, Long.parseLong(pid)))){
            log.info("用户无权限");
            throw new AvalonException(AvalonStatus.AUTH_NOT_ALLOW);
        }

    }
}
