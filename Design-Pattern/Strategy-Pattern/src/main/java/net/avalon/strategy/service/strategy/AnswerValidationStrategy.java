package net.avalon.strategy.service.strategy;

/**
 * @Author: Heda
 * @Create: 2024/10/15
 */
public interface AnswerValidationStrategy {

    boolean validate(String userAnswer, String correctAnswer);
}
