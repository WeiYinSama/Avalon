package net.avalon.strategy.service.strategy.impl;

import net.avalon.strategy.service.strategy.AnswerValidationStrategy;
import net.avalon.strategy.service.strategy.QuestionType;
import org.springframework.stereotype.Component;

/**
 * 单选
 *
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Component
@QuestionType("0")
public class SingleChoiceValidation implements AnswerValidationStrategy {

    @Override
    public boolean validate(String userAnswer, String correctAnswer) {
        return userAnswer.equals(correctAnswer);
    }
}
