package net.avalon.strategy.service.strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标记每个策略实现类对应的题型标记
 *
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestionType {

    String value();  // 题型标记
}
