package net.avalon.strategy.service.strategy.impl;

import net.avalon.strategy.service.strategy.AnswerValidationStrategy;
import net.avalon.strategy.service.strategy.QuestionType;
import org.springframework.stereotype.Component;

/**
 * 多选
 *
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Component
@QuestionType("2")
public class MultipleChoiceValidation implements AnswerValidationStrategy {
    @Override
    public boolean validate(String userAnswer, String correctAnswer) {
        return false;
    }
}
