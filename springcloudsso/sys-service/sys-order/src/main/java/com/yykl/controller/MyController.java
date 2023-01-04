package com.yykl.controller;

import com.yyl.util.Result;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {


    @GetMapping("test")
    @Secured("ROLE_admin")
    public Result test(){
        return new Result("test一下sso");
    }


    @Secured("ROLE_user")
    @GetMapping("test1")
    public Result test1(){
        return new Result("test一下sso!!!!!!!");
    }

    @Secured("ROLE_admin")
    @GetMapping("test2")
    public Result test2(Principal principal){
        return new Result(principal);
    }
}

