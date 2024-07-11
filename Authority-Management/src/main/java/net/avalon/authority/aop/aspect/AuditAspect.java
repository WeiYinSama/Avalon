package net.avalon.authority.aop.aspect;


import jakarta.servlet.http.HttpServletRequest;
import net.avalon.generic.core.exception.AvalonException;
import net.avalon.generic.core.exception.AvalonStatus;
import net.avalon.generic.core.util.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/27 - 13:41
 */
@Aspect
@Component
@Order(20)
public class AuditAspect {


    /**
     * 身份校验
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("net.avalon.authority.aop.CommonPointCuts.audit()")
    public Object aroundAudit(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //从请求头拿到 token
        String token = request.getHeader(JwtUtil.LOGIN_TOKEN_KEY);

        // 校验token
        JwtUtil.TokenInfo tokenInfo = checkToken(token);

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        String[] paramNames = ms.getParameterNames();
        Object[] args = pjp.getArgs();

        initUserInfo(tokenInfo, args, paramNames);

        return pjp.proceed(args);
    }

    /**
     * 将 tokenInfo 中的用户信息传递给方法
     * @param tokenInfo
     * @param args
     * @param paramNames
     */
    private void initUserInfo(JwtUtil.TokenInfo tokenInfo, Object[] args, String[] paramNames) {

        // 将方法中参数名为 formId 的参数值 赋值给临时变量 formId
        for (int i = 0; i < paramNames.length; i++) {
            if ("user".equals(paramNames[i])) {
                args[i] = tokenInfo.getUser();
                break;
            }
        }
    }

    /**
     * 检查token
     * @param token
     * @return
     * @throws AvalonException
     */
    private JwtUtil.TokenInfo checkToken(String token) throws AvalonException{
        JwtUtil.TokenInfo tokenInfo = JwtUtil.verifyToken(token);
        if (null == tokenInfo){
            throw new AvalonException(AvalonStatus.AUTH_INVALID_JWT,"token 校验异常，请重新登录");
        }
        return tokenInfo;
    }

}
