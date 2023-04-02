package net.avalon.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: Weiyin
 * @Create: 2023/3/22 - 17:59
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 缓存的几个问题：
     * 1. 查询的数据未缓存，需要访问数据库
     * 2. 缓存的数据同时过期
     * 3.
     */
    @Test
    public void test() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        Wife w1 = new Wife(1L, "露娜", 14, "weiyin の 老婆~", List.of("偶像"));

        String s = JacksonUtil.toJson(w1);

        Random random = new Random();

        ops.set("wife", s);

        String value = ops.get("wife");

        Wife w2 = JacksonUtil.toObj(value, Wife.class);
        System.out.println(w2);
    }
}
