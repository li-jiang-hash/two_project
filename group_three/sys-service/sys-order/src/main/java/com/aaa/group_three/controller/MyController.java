package com.aaa.group_three.controller;

import com.aaa.util.Result;
import org.springframework.web.bind.annotation.GetMapping;

public class MyController {

    @GetMapping("test")
    public Result test(){
        return new Result("测试一次sso");

    }
}
