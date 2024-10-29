package net.avalon.strategy.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Heda
 * @Create: 2024/10/15
 */
@SpringBootTest
@Slf4j
public class AnswerValidationStrategyContextTest {

    @Autowired
    private AnswerValidationStrategyContext context;

    @Test
    void testContext() {
        String userAnswer = "正确^错误^嘻嘻";
        String correctAnswer = "正确,错误,嘻嘻";

        boolean result = context.checkAnswer("3",userAnswer,correctAnswer);

        log.info("自动判题结果：{}", result);

    }
}