package com.aaa.group_three.controller;


import com.aaa.entity.GSort;
import com.aaa.group_three.service.impl.GSortServiceImpl;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品类别 前端控制器
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/g-sort")
public class GSortController {
    @Resource
    private GSortServiceImpl gSortService;
//   查询商品类型
    @GetMapping("sort")
    public Result findAll(){
        return new Result(gSortService.list());
    }
}

