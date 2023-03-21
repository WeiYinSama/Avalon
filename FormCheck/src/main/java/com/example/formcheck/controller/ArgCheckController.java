package com.example.formcheck.controller;

import com.example.formcheck.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ArgCheckController {


    @PostMapping
    public Object addUser(@Valid @RequestBody User user){
        System.out.println(user);
        return user;
    }
}
