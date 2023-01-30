package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyl.entity.TUser;
import com.yyl.systemdemo.service.ITUserService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@RestController
@RequestMapping("/t-user")
public class TUserController {

    @Resource
    private ITUserService userService;

    // 根据用户名 获取 用户信息
    @GetMapping("getUserByName/{username}")
    public TUser getUser(@PathVariable String username){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
       return userService.getOne(queryWrapper);
    }

}

