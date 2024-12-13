package net.avalon.gateway;

import net.avalon.gateway.client.UserClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringCloudGatewayApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserClient userClient;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("Ciallo~","WeiYin");
    }

    @Test
    void opsForHash() {

        redisTemplate.opsForHash().get("Priv", "/user-GET");


    }

    @Test
    void rpc() {
        userClient.loadPidsIfAbsent(7L);
    }
}
