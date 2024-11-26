package net.avalon.feign.controller;

import net.avalon.feign.client.DemoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Heda
 * @Create: 2024/11/26
 */
@RestController
public class FeignController {


    @Autowired
    private DemoClient demoClient;

    @GetMapping("/feign/demo")
    public String hello() {
        return demoClient.hello();
    }
}
