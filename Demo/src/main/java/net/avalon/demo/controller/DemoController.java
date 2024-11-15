package net.avalon.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Heda
 * @Create: 2024/11/15
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @GetMapping("/hi")
    public String hello() {
        return "Hello, Spring Boot!";
    }


    @GetMapping("/hello/{name}")
    public String hi(@PathVariable("name") String name) {
        //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("demo");
        String url = String.format("http://%s:%s/hi",serviceInstance.getHost(),serviceInstance.getPort());
        System.out.println("request url:"+url);
        return restTemplate.getForObject(url,String.class) + "From: " + name;

    }

    @PostMapping("load/{id}")
    public void load(@PathVariable("id") Long id){
        log.info("加载用户 {} 权限",id);
    }
}
