package net.avalon.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Heda
 * @Create: 2024/11/26
 */
@FeignClient("demo")
public interface DemoClient {

    @GetMapping("/hi")
    String hello();
}
