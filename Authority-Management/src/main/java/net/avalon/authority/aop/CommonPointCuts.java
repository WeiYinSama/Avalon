package net.avalon.authority.aop;

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

    @Pointcut("execution(* net.avalon.authority.controller.*.*(..))")
    public void controller(){}


    @Pointcut("@annotation(net.avalon.authority.aop.annotation.FormId)")
    public void formHasBeenSubmitted(){}


    @Pointcut("execution(* net.avalon.authority.controller.*.*(..))")
    public void auth(){}

    @Pointcut("@annotation(net.avalon.authority.aop.annotation.Audit)")
    public void audit(){}
}
