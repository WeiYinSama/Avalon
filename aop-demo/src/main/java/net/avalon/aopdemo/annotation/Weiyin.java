package net.avalon.aopdemo.annotation;

import net.avalon.aopdemo.util.Convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author weiyin
 * @time 2022/12/3 - 21:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Weiyin {

    String desc() default "";
    Class<? extends Convert> convert();
}
