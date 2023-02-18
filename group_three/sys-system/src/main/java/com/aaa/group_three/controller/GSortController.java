package com.aaa.group_three.controller;


import com.aaa.entity.GBrand;
import com.aaa.entity.GGoods;
import com.aaa.entity.GSort;
import com.aaa.group_three.service.impl.GSortServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/sort")
    public Result findAll(){
        return new Result(gSortService.list());
    }

//    查询类别表中数据
    @PostMapping("findSorts")
    public Result getAllSorts(PageInfo pageInfo,GSort gSort){
        QueryWrapper<GSort> queryWrapper=new QueryWrapper();
        if (gSort.getSortname()!=null){
            queryWrapper.eq("sortname",gSort.getSortname());

        }
        if(gSort.getStatus()!=null){
            queryWrapper.eq("status",gSort.getStatus());
        }
        Page list = gSortService.page(pageInfo,queryWrapper);
        return new Result(list);
    }
//    查询类别名称
    @GetMapping("findSort")
    public Result getAllSort(){
        QueryWrapper<GSort> queryWrapper=new QueryWrapper();
        queryWrapper.select("sortname");
        return new Result(gSortService.list(queryWrapper));
    }
//    类别审核操作
    @PostMapping("shenhe")
    public Result getId(@RequestBody GSort gSort){
        return new Result(gSortService.updateById(gSort));
    }
    //    逻辑删除类别管理表中数据
    @PostMapping("{id}")
    public Result del(@PathVariable String id){
        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_deleted","1");
        updateWrapper.eq("id",id);
        boolean byId = gSortService.update(updateWrapper);
        return new Result(byId);
    }

    @GetMapping("getSort")
    public Result getSort(){
        return new Result(gSortService.list());
    }
}

