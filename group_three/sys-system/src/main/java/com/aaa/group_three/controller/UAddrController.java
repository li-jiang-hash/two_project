package com.aaa.group_three.controller;


import com.aaa.group_three.service.impl.UAddrServiceImpl;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/addr")
public class UAddrController {

    @Resource
    private UAddrServiceImpl uAddrService;

    //查询地址
    @GetMapping("/findAll")
    public Result findAll(String id){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        return new Result(uAddrService.list(queryWrapper));
    }
}

