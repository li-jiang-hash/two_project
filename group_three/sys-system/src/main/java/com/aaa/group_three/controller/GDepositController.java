package com.aaa.group_three.controller;


import com.aaa.entity.GDeposit;
import com.aaa.entity.GSort;
import com.aaa.group_three.service.impl.GDepositServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 保存方式 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/g-deposit")
public class GDepositController {
    @Resource
    private GDepositServiceImpl gDepositService;
    //    查询储藏表中数据
    @PostMapping("findDeposits")
    public Result getAllSorts(PageInfo pageInfo, GDeposit gDeposit){
        QueryWrapper<GDeposit> queryWrapper=new QueryWrapper();
        queryWrapper.eq("is_deleted",0);
        if (gDeposit.getDepositname()!=null){
            queryWrapper.eq("depositname",gDeposit.getDepositname());
        }
        if(gDeposit.getStatus()!=null){
            queryWrapper.eq("status",gDeposit.getStatus());
        }
        Page list = gDepositService.page(pageInfo,queryWrapper);
        return new Result(list);
    }
    //    查询储藏名称
    @GetMapping("/findAllDeposit")
    public Result getAllSort(){
        QueryWrapper<GDeposit>  queryWrapper=new QueryWrapper();
        queryWrapper.select("depositname");
        return new Result(gDepositService.list(queryWrapper));
    }
    //    储藏审核操作
    @PostMapping("shenhe")
    public Result getId(@RequestBody GDeposit gDeposit){
        return new Result(gDepositService.updateById(gDeposit));
    }
    //    逻辑删除储藏管理表中数据
    @PostMapping("{id}")
    public Result delDepositname(@PathVariable String id){
        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_deleted","1");
        updateWrapper.eq("id",id);
        boolean byId = gDepositService.update(updateWrapper);
        return new Result(byId);
    }
    @PostMapping("/findAllDeposit")
    public Result getAllDeposit(){
        return new Result(gDepositService.list());
    }

}

