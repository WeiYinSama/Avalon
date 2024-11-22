package net.avalon.gateway.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 *
 * @Author: Heda
 * @Create: 2024/11/22
 */
@Component
@Slf4j
public class TestFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Instant startTime = Instant.now();

        log.info("startTime:{}", LocalDateTime.now());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Instant endTime = Instant.now();
            long millis = Duration.between(startTime, endTime).toMillis();
            log.info("Request took {} ms",millis);
        }));
    }

    /**
     *
     * @return 值越低 优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
