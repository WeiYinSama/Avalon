package net.avalon.gateway.filter.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 请求日志过滤器
 *
 * 记录：
 *      1. 请求路径
 *      2. 请求方法
 *      3. IP 地址
 *      4.
 *
 * @Author: Heda
 * @Create: 2024/11/15
 */
//@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logRequest(exchange);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private void logRequest(ServerWebExchange exchange) {
        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();
        String ip = getClientIp(exchange);
        String protocol = "HTTP/1.1";

        // 请求头遍历
        StringBuilder headers = new StringBuilder();
        for (Map.Entry<String, String> entry : exchange.getRequest().getHeaders().toSingleValueMap().entrySet()) {
            headers.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // 请求报文体读取
        String body = getRequestBody(exchange);

        // 打印请求日志
        log.info("RemoteIP: {}",ip);
        log.info("{} {} {}", method, path, protocol);
        log.info("{}", headers.toString());

        if (!body.isEmpty()) {
            log.info("Content-Length: {}", body.length());
            log.info("Request Body:\n{}", body);
        }
    }

    private String getClientIp(ServerWebExchange exchange) {
        // 从请求中获取 IP 地址
        if (exchange.getRequest().getRemoteAddress() != null) {
            return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        }
        return "Unknown IP"; // 如果无法获取 IP，返回默认值
    }

    private String getRequestBody(ServerWebExchange exchange) {
        // 由于请求体是一个流，我们需要缓存请求体来进行日志打印
        try {
            if (exchange.getRequest().getMethod().equals(HttpMethod.POST) ||
                    exchange.getRequest().getMethod().equals(HttpMethod.PUT)) {

                // 获取请求体内容并转换为字符串
                return exchange.getRequest().getBody()
                        .map(buffer -> buffer.toString(StandardCharsets.UTF_8)) // 将请求体转换为 UTF-8 字符串
                        .reduce("", String::concat).block();
            }
        } catch (Exception e) {
            log.error("Error reading request body", e);
        }
        return ""; // 如果没有请求体或者是 GET 请求则返回空
    }
}
