package com.aaa.group_three.controller;

import com.aaa.entity.TUser;
import com.aaa.group_three.service.ITUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:50:33
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping({"/t-user"})
public class TUserController {
    @Resource
    private ITUserService userService;

    public TUserController() {
    }

    @GetMapping({"getUserByName/{username}"})
    public TUser getUser(@PathVariable String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return (TUser)this.userService.getOne(queryWrapper);
    }
}
