package net.avalon.gateway.client;

import net.avalon.generic.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
*
* @Author: Heda
* @Create: 2024/11/25
*/
@Service
public class UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);
    @Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public Mono<Void> loadPrivA() {
        return WebClient.builder()
                .baseUrl("http://demo")
                .filter(lbFunction)
                .build()
                .post().uri("/load/{id}", 1).retrieve().bodyToMono(Void.class);
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public Mono<Void> loadPrivB() {
        return webClientBuilder.build().post().uri("http://demo/load/{id}",1).retrieve().bodyToMono(Void.class);
    }


    public void loadPidsIfAbsent(Long uid){

        String key = "u_" + uid;
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            webClientBuilder.build().get()
                    .uri("http://avalon-user/loadpriv/{id}", uid)  // 将 uid 作为路径参数传递
                    .retrieve()  // 发送 GET 请求
                    .bodyToMono(Void.class)  // 响应体为 Void 类型
                    .doOnTerminate(() -> {
                        // 请求完成后的处理逻辑（例如打印日志或其他操作）
                        System.out.println("Privilege loading completed for user ID: " + uid);
                    })
                    .subscribe();  // 订阅以触发请求
        }

    }
}
