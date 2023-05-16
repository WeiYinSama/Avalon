package net.avalon.redis;

import lombok.extern.slf4j.Slf4j;
import net.avalon.redis.domain.Wife;
import net.avalon.redis.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 扣减库存 Lua 脚本
     */
    private static final String STOCK_LUA;

    static {

        StringBuilder sb = new StringBuilder();
        sb.append("local count = tonumber(redis.call('get',KEYS[1])) ");
        sb.append("local quantity = tonumber(ARGV[1]) ");
        sb.append("if count >= quantity then ");
        sb.append("    return redis.call('decrby', KEYS[1], quantity) ");
        sb.append("else ");
        sb.append("    return -1 ");
        sb.append("end");
        STOCK_LUA = sb.toString();
    }

    /**
     * 对象的存取
     * <p>
     * object -> json
     * json -> object
     */
    @Test
    public void test() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        Wife w1 = new Wife(1L, "露娜", 14, "weiyin の 老婆~", LocalDateTime.now(), List.of("偶像"));
        String s = JacksonUtil.toJson(w1);

        ops.set("wife", s);

        String value = ops.get("wife");

        Wife w2 = JacksonUtil.toObj(value, Wife.class);
        System.out.println(w2);
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
                        log.info("Status: {}", product);
                        return product;
                    }
            );
        }

        ExecutorService pool = Executors.newFixedThreadPool(1000);

        pool.invokeAll(users);

    }

    /**
     * 即使输入的值是字符串，但只要能被解释为整数，就可以将它当作整数处理
     */
    @Test
    void stringRedisTemplateTest() {

        Wife wife = new Wife();
        wife.setName("Miku");
        wife.setCreateTime(LocalDateTime.now());

        stringRedisTemplate.opsForValue().set("wife",JacksonUtil.toJson(wife));

        String w1 = stringRedisTemplate.opsForValue().get("wife");
        log.info("w1 = " + w1);
    }

    /**
     * 编码方式：
     * listpack    <= 128
     * skiplist     > 128
     */
    @Test
    void zset() {
        Random random = new Random();

        for (int i = 0; i < 128; i++) {
            int randomScore = random.nextInt(1000);
            redisTemplate.opsForZSet().add("z1",i,randomScore);
        }
    }

    /**
     * 编码方式：
     * > 512   hashtable
     * <= 512  listpack
     */
    @Test
    void hash() {
        Random random = new Random();

        for (int i = 0; i < 513; i++) {
            int randomScore = random.nextInt(1000);
            redisTemplate.opsForHash().put("h1",String.format("%s",i),randomScore);
        }
    }

}
