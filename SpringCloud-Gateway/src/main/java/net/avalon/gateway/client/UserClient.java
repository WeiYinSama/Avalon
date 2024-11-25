package net.avalon.gateway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
*
* @Author: Heda
* @Create: 2024/11/25
*/
@Service
public class UserClient {

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

    public Mono<Void> loadPrivB() {
        return webClientBuilder.build().post().uri("http://demo/load/{id}",1).retrieve().bodyToMono(Void.class);
    }
}
