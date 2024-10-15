package net.avalon.strategy.service.strategy.impl;

import net.avalon.strategy.service.strategy.AnswerValidationStrategy;
import net.avalon.strategy.service.strategy.QuestionType;
import org.springframework.stereotype.Component;

/**
 * 填空
 *
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Component
@QuestionType("3")
public class FillInBlankValidation implements AnswerValidationStrategy {
    @Override
    public boolean validate(String userAnswer, String correctAnswer) {

        userAnswer = String.join(",", userAnswer.split("\\^"));

        return userAnswer.equals(correctAnswer);
    }
}
