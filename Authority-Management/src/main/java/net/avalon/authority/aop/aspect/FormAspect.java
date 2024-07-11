package net.avalon.authority.aop.aspect;

import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @Author: Weiyin
 * @Create: 2023/5/30 - 14:16
 */
@Aspect
@Component
public class FormAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Around("net.avalon.authority.aop.CommonPointCuts.formHasBeenSubmitted()")
    public Object aroundAudit(ProceedingJoinPoint pjp) throws Throwable {

        String formId = getFormId(pjp);

        // 使用redis来做表单的防重复提交，缓存时间20s
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(formId, "", Duration.ofSeconds(20));
        if (!absent) {
            throw new AvalonException(AvalonStatus.FORM_HAS_BEEN_SUBMITTED);
        }

        return pjp.proceed();
    }

    private String getFormId(ProceedingJoinPoint pjp){

        String formId = null;
        // 拿到方法参数名
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        String[] paramNames = ms.getParameterNames();
        // 拿到参数值
        Object[] args = pjp.getArgs();

        // 将方法中参数名为 formId 的参数值 赋值给临时变量 formId
        for (int i = 0; i < paramNames.length; i++) {
            if ("formId".equals(paramNames[i])) {
                formId = (String) args[i];
            }
        }
        return formId;
    }
}
