package net.avalon.authority.service;

import lombok.extern.slf4j.Slf4j;
import net.avalon.generic.core.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: Weiyin
 * @Create: 2024/1/29 - 8:32
 */
@SpringBootTest
@Slf4j
class UserServiceTest {

    private static final String ADMIN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLouqvku73moKHpqowiLCJhdWQiOiJBdmFsb24iLCJ1aWQiOjEsImlzcyI6IkxhY2lhIiwibmFtZSI6IkxhY2lhIiwiZXhwIjoxNzA2NTE3NjMxLCJpYXQiOjE3MDY0ODg4MzF9.dfKBp0BQqQySnp9bIJdCIKcNEjfSTH-rNj4nZIFwI6I";

    private static final String USER = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLouqvku73moKHpqowiLCJhdWQiOiJBdmFsb24iLCJ1aWQiOjIsImlzcyI6IkxhY2lhIiwibmFtZSI6IldlaVlpbiIsImV4cCI6MTcwNjUyNDcyMCwiaWF0IjoxNzA2NDk1OTIwfQ.zTogf4tCgB4c8nZJCDnbvwGN2LF0KUfqFKuuStZgD3E";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void authenticate() {
        JwtUtil.User user = new JwtUtil.User();
        user.setId(2L);
        user.setName("WeiYin");

        String token = JwtUtil.createToken(user, 8 * 60 * 60);
        log.debug("生成的 token：{}", token);

        JwtUtil.TokenInfo info = JwtUtil.verifyToken(token);
        if (info != null) {
            log.debug("验证通过");
            log.info("tokenInfo：{}", info);
        }

    }

    @Test
    void loadPrivsOfUser() {

        Long[] ids = {5L, 7L, 8L, 10L};


        redisTemplate.opsForSet().add("u_2", ids);


    }
}