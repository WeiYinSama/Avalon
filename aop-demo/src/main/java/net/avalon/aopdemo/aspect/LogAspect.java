package net.avalon.aopdemo.aspect;

import net.avalon.aopdemo.annotation.Weiyin;
import net.avalon.aopdemo.domain.OperateLog;
import net.avalon.aopdemo.util.Convert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weiyin
 * @time 2022/12/3 - 21:59
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 1.定义切入点
     * 2.横切逻辑
     * 3.织入
     */
    @Pointcut("@annotation(net.avalon.aopdemo.annotation.Weiyin)")
    public void log() {
    }

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    @Around("log()")
    public Object arount(ProceedingJoinPoint pjp) throws Throwable {
        Object o = pjp.proceed();
        threadPoolExecutor.execute(() -> {

            try {
                MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
                Weiyin weiyin = methodSignature.getMethod().getAnnotation(Weiyin.class);
                Class<? extends Convert> convert = weiyin.convert();
                Convert logConvert = convert.newInstance();

                OperateLog operateLog = logConvert.convert(pjp.getArgs()[0]);
                operateLog.setDesc(weiyin.desc());
                operateLog.setResult(o.toString());

                System.out.println("insert operationLog " + operateLog.toString());

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return o;
    }
}
