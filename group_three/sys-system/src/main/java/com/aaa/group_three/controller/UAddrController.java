package com.aaa.group_three.controller;


import com.aaa.entity.UAddr;
import com.aaa.group_three.service.impl.UAddrServiceImpl;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/findById")
    public Result findById(String id){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        queryWrapper.eq("is_deleted",0);
        return new Result(uAddrService.list(queryWrapper));
    }
    @PostMapping("/updateById")
    public Result insertAddr(@RequestBody UAddr info){


        if (info.getIsDeleted() == null){
            info.setIsDeleted(0);
        }
        return new Result(uAddrService.saveOrUpdate(info));
    }
    @GetMapping("/deleteById")
    public Result DelById(String id){
        UAddr uAddr = new UAddr();
        uAddr.setId(id);
        uAddr.setIsDeleted(1);
        return new Result(uAddrService.updateById(uAddr));
    }
}

