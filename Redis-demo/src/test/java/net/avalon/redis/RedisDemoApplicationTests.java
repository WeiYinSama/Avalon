package net.avalon.redis;

import lombok.extern.slf4j.Slf4j;
import net.avalon.redis.domain.Wife;
import net.avalon.redis.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //相同的对象，使用redisTemplate 141B
    //           使用stringRedisTemplate 101B，能节省 1/3 空间

    @Test
    void redisTemplateTest() {
        Wife wife = new Wife();
        wife.setName("Miku");
        wife.setAge(21);
        wife.setCreateTime(LocalDateTime.now());

        redisTemplate.opsForValue().set("wife",wife);

        Wife w = (Wife)redisTemplate.opsForValue().get("wife");
        System.out.println(w);

    }

    @Test
    void stringRedisTemplateTest() {
        Wife wife = new Wife();
        wife.setName("Miku");
        wife.setAge(21);
        wife.setCreateTime(LocalDateTime.now());

        String w1 = JacksonUtil.toJson(wife);
        stringRedisTemplate.opsForValue().set("wife",w1);

        String w2 = stringRedisTemplate.opsForValue().get("wife");
        wife = JacksonUtil.toObj(w2, Wife.class);
        System.out.println(wife);
    }
}
