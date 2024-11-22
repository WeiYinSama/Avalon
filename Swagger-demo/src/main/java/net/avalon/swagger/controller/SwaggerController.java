package net.avalon.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Heda
 * @Create: 2024/11/22
 */
@RestController
public class SwaggerController {


    @GetMapping("/hi")
    public String hi(){
        return "Hello, Swagger!";
    }
}
