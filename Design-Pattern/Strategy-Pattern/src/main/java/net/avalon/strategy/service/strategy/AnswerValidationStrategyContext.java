package net.avalon.strategy.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Component
@Slf4j
public class AnswerValidationStrategyContext implements ApplicationContextAware {

    private final Map<String, AnswerValidationStrategy> strategyMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // 获取所有 AnswerValidationStrategy 的实现类
        Map<String, AnswerValidationStrategy> beans = context.getBeansOfType(AnswerValidationStrategy.class);
        //
        for (AnswerValidationStrategy strategy : beans.values()) {
            QuestionType annotation = strategy.getClass().getAnnotation(QuestionType.class);
            if (annotation != null) {
                // 根据注解的值注册策略
                strategyMap.put(annotation.value(), strategy);
            } else {
                log.warn("{} 缺少 @QuestionType 注解",strategy.getClass().getName());
            }
        }
    }

    private AnswerValidationStrategy getStrategy(String questionType) {
        AnswerValidationStrategy strategy = strategyMap.get(questionType);
        if (strategy == null) {
            log.warn("未找到对应的校验策略");
            throw new RuntimeException("未找到对应的校验策略: " + questionType);
        }
        return strategy;
    }

    public boolean checkAnswer(String questionType, String userAnswer, String correctAnswer) {
        AnswerValidationStrategy validator = getStrategy(questionType);
        return validator.validate(userAnswer, correctAnswer);
    }
}
