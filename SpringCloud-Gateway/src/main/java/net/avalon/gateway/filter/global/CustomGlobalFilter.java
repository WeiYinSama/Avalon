package net.avalon.gateway.filter.global;


import lombok.extern.slf4j.Slf4j;
import net.avalon.gateway.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Heda
 * @Create: 2024/11/15
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter {


    @Autowired
    private UserClient userClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return userClient.loadPrivA().then(chain.filter(exchange));

    }


}
