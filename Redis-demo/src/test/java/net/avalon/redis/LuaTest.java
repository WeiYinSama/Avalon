package net.avalon.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Weiyin
 * @Create: 2023/5/5 - 15:24
 */
@SpringBootTest
@Slf4j
public class LuaTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    private static final DefaultRedisScript<Long> script;

    static {
        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("train-demo.lua"));
        script.setResultType(Long.class);
    }

    @Test
    void test1() throws InterruptedException {

        //准备10件商品
        redisTemplate.opsForValue().set("product", 10);

        //准备1000个任务抢商品
        List<Callable<Long>> users = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            users.add(
                    () -> {
                        Long product = redisTemplate.execute(script, List.of("product"), 1);
                        log.info("Status: {}", product);
                        return product;
                    }
            );
        }

        ExecutorService pool = Executors.newFixedThreadPool(1000);

        pool.invokeAll(users);
    }
}
