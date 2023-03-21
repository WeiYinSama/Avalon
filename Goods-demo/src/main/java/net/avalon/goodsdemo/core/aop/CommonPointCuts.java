package net.avalon.goodsdemo.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/24 - 20:43
 */
@Aspect
public class CommonPointCuts {

    @Pointcut("execution(* net.avalon.goodsdemo.controller.*.*(..))")
    public void controller(){}

    @Pointcut("@annotation(net.avalon.goodsdemo.core.aop.annotation.Audit)")
    public void audit(){}
}
