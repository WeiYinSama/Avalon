package net.avalon.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiyin
 * @time 2022/11/29 - 21:46
 */
@RestController
public class SwaggerDemoController {

    @GetMapping("/hello")
    public String hello(){
        return String.format("hello,world");
    }
}
