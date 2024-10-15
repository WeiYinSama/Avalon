package net.avalon.strategy.service.strategy.impl;

import net.avalon.strategy.service.strategy.AnswerValidationStrategy;
import net.avalon.strategy.service.strategy.QuestionType;
import org.springframework.stereotype.Component;

/**
 * 问答
 *
 * @Author: Heda
 * @Create: 2024/10/15
 */
@Component
@QuestionType("4")
public class EssayValidation implements AnswerValidationStrategy {
    @Override
    public boolean validate(String userAnswer, String correctAnswer) {
        return false;
    }
}
