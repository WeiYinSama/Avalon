package net.avalon.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Weiyin
 * @Create: 2023/3/22 - 17:59
 */
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 扣减库存 Lua 脚本
     */
    private static final String STOCK_LUA;

    static {

        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call('get', KEYS[1]) >= ARGV[1] then ");
        sb.append("    return redis.call('decrby', KEYS[1], ARGV[1]) ");
        sb.append("else ");
        sb.append("    return -1 ");
        sb.append("end");
        STOCK_LUA = sb.toString();
    }

    /**
     * 缓存的几个问题：
     * 1. 查询的数据未缓存，需要访问数据库
     * 2. 缓存的数据同时过期
     * 3.
     */
    @Test
    public void test() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        Wife w1 = new Wife(1L, "露娜", 14, "weiyin の 老婆~", List.of("偶像"));
        String s = JacksonUtil.toJson(w1);

        ops.set("wife", s);

        String value = ops.get("wife");

        Wife w2 = JacksonUtil.toObj(value, Wife.class);
        System.out.println(w2);
    }


    @Test
    public void countTest() {

//        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        ValueOperations<String, Serializable> ops = redisTemplate.opsForValue();

        String s = UUID.randomUUID().toString();
        System.out.println(s);

        ops.set(s, 100);

        for (int i = 0; i < 100; i++) {
            ops.increment(s, -2);
        }

        System.out.println(ops.get(s));

    }

    /**
     * 扣库存实战
     * 1000个用户枪10件商品
     */
    @Test
    public void stock() throws InterruptedException {

        //准备脚本
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(STOCK_LUA);
        script.setResultType(Long.class);

        //准备10件商品
        redisTemplate.opsForValue().set("product", 10);

        //准备1000个任务抢商品
        List<Callable<Long>> users = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            users.add(
                    () -> {
                        Long product = redisTemplate.execute(script, List.of("product"), 1);
                        log.info("Status: {}",product);
                        return product;
                    }
            );
        }

        ExecutorService pool = Executors.newFixedThreadPool(1000);

        pool.invokeAll(users);

    }
}
