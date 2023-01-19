package com.yyl.systemdemo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegisterController {
    @PostMapping("/signInsert/{phone}/{password}")
    public String Register(@PathVariable String phone, @PathVariable String password){
        System.out.println(phone+" "+password);
        return null;
    }
}
