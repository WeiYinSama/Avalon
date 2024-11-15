package net.avalon.gateway.filter.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

/**
 * @Author: Heda
 * @Create: 2024/11/15
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter {


    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取 demo 实例 の 地址
        List<ServiceInstance> instances = discoveryClient.getInstances("demo");
        String url = instances.get(new Random().nextInt(instances.size())).getUri().toString();
        // 构建 webClient
        WebClient webClient = webClientBuilder.baseUrl(url).build();

        return webClient.post().uri("/load/{id}",1).retrieve().bodyToMono(Void.class).then(chain.filter(exchange));

    }


}
