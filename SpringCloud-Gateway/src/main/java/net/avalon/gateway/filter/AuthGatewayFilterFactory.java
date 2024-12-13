package net.avalon.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.avalon.gateway.client.UserClient;
import net.avalon.generic.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.alibaba.nacos.api.common.Constants.TOKEN;

/**
 * @Author: Heda
 * @Create: 2024/11/22
 */
@Slf4j
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserClient userClient;

    public AuthGatewayFilterFactory() {
        super(Config.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return super.shortcutFieldOrder();
    }

    /**
     * 验证和授权,权限过滤器
     * 0. 看用户想干啥
     * 1. 校验 token 是否需要换发token
     * 2. 从redis中读取用户权限信息，如果该用户的权限信息不在redis中，调用微服务load用户权限信息
     * 3. 判断用户是否有权限访问该url
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            /* *************** 一、检查 hk = path + method in Priv ? *************** */
            //1.get path
            String path = request.getURI().getPath();
            //2.get method
            String method = request.getMethod().name();
            log.info("{}-{}", path, method);

            //3.path format,数字 -> /{id} == url
            String url = this.path2Url(path);
            log.info("url -> {}", url);

            //4.get hashkey = url + "-" + method
            String hk = url + "-" + method;
            //5.get pid
            String pid = this.getPid(hk);   //long pid

            if (pid == null) {
                //检查hk是否存在，可以放到全局过滤器中
                response.setRawStatusCode(404);
                return response.writeWith(Mono.empty());
            }

            /* *************** 2. 校验token *************** */
            //1. 从请求头获取token
            String token = request.getHeaders().getFirst(JwtUtil.LOGIN_TOKEN_KEY);
            //2. 校验token是否合法
            JwtUtil.TokenInfo tokenInfo = JwtUtil.verifyToken(token);
            if (tokenInfo == null) {
                //token 不合法
                response.setRawStatusCode(401);
                return response.writeWith(Mono.empty());
            }

            //3. 获得uid和过期时间
            Long uid = tokenInfo.getUser().getId();

            Date expireAt = tokenInfo.getExpiresAt();
            //4. 检查是否需要换发token,16、15
            // todo 将旧的token扔掉
            long timeleft = expireAt.getTime() - System.currentTimeMillis();
            if (timeleft < 2 * 24 * 60 * 60 * 1000L) {
                token = JwtUtil.createToken(tokenInfo.getUser(), 16 * 24 * 60 * 60);
            }
            response.getHeaders().set(TOKEN, token);

            /* *************** 3. 用户权限信息（uid:pids） in redis? *************** */
            //检查redis中是否有用户的权限信息，没有则通过RPC调用用户服务加载数据
            //可以改成先get再异步load
            userClient.loadPidsIfAbsent(uid);

            /* *************** 4. pid in pids? *************** */
            if(!this.checkPermissons(uid, pid)){
                response.setRawStatusCode(403);
                return response.writeWith(Mono.empty());
            }else{
                return chain.filter(exchange);
            }
        };
    }

    private String getPid(String hk) {

        if (redisTemplate.opsForHash().hasKey("Priv", hk)) {
            return redisTemplate.opsForHash().get("Priv", hk).toString();
        } else {
            return null;
        }

    }

    /**
     * path -> privilege
     *
     * @param path
     * @return
     */
    private String path2Url(String path) {

        String ret;

        Pattern p1 = Pattern.compile("/[a-f0-9-]{36}.[a-z]{3,4}");
        ret = p1.matcher(path).replaceAll("/{id}");

        Pattern p2 = Pattern.compile("/[1-9][0-9]*");
        ret = p2.matcher(ret).replaceAll("/{id}");

        return ret;
    }

    /**
     * 检查用户是否有对应的权限
     * @param uid
     * @param pid
     * @return
     */
    public boolean checkPermissons(Long uid,String pid){

        String key = "u_" + uid;
        if(Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, Integer.parseInt(pid)))){
            return true;
        }else{
            return false;
        }
    }

    public static class Config {
    }
}
