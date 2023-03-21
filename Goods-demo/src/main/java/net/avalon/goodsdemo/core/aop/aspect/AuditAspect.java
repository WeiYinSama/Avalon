package net.avalon.goodsdemo.core.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import net.avalon.goodsdemo.core.aop.annotation.LoginName;
import net.avalon.goodsdemo.core.aop.annotation.LoginUser;
import net.avalon.goodsdemo.util.AvalonException;
import net.avalon.goodsdemo.util.AvalonStatus;
import net.avalon.goodsdemo.util.JwtUtil;
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
    @Around("net.avalon.goodsdemo.core.aop.CommonPointCuts.audit()")
    public Object aroundAudit(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //从请求头拿到 token
        String token = request.getHeader(JwtUtil.LOGIN_TOKEN_KEY);

        JwtUtil.TokenInfo tokenInfo = checkToken(token);

        Object[] args = pjp.getArgs();

        initUserInfo(tokenInfo, args, method);

        return pjp.proceed(args);
    }

    /**
     * 将 tokenInfo 中的用户信息传递给方法
     * @param tokenInfo
     * @param args
     * @param method
     */
    private void initUserInfo(JwtUtil.TokenInfo tokenInfo, Object[] args, Method method) {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        //外层循环遍历方法所有参数
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];

            for (Annotation annotation : parameterAnnotation) {
                //内层循环遍历参数所有注解

                //这里判断当前注解是否为LoginUser.class
                if (annotation.annotationType().equals(LoginUser.class)) {
                    args[i] = tokenInfo.getUser().getId();
                    break;
                }
                if (annotation.annotationType().equals(LoginName.class)) {
                    args[i] = tokenInfo.getUser().getName();
                    break;
                }
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
        if (null == token){
            throw new AvalonException(AvalonStatus.AUTH_NEED_LOGIN);
        }
        JwtUtil.TokenInfo tokenInfo = JwtUtil.verifyToken(token);
        if (null == tokenInfo){
            throw new AvalonException(AvalonStatus.AUTH_INVALID_JWT);
        }
        return tokenInfo;
    }

}
