package net.avalon.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Weiyin
 * @Create: 2024/6/22 - 16:59
 */
@RestController
public class AlohaController {



    @GetMapping("/hi")
    public String sayHi(){
        return "Ciallo~";
    }
}
