package com.aaa.group_three.controller;


import com.aaa.entity.GBrand;
import com.aaa.entity.GGoods;
import com.aaa.group_three.service.impl.GBrandServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/g-brand")
public class GBrandController {
    @Resource
    private GBrandServiceImpl gBrandService;
    //    查询品牌表中数据
    @PostMapping("findAllBrands")
    public Result getAllApp(PageInfo pageInfo,GBrand gBrand){
        Page list = gBrandService.getAllBrands(pageInfo, gBrand);
        return new Result(list);
    }
    //   查询商品类型
    @PostMapping("brand")
    public Result findAll(){
        return new Result(gBrandService.list());

}
    //    品牌管理审核操作
    @PostMapping("shenhe")
    public Result getId(@RequestBody GBrand gBrand){
        return new Result(gBrandService.updateById(gBrand));
    }
    //    逻辑删除品牌管理表中数据
    @PostMapping("{id}")
    public Result del(@PathVariable String id){
        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_deleted","1");
        updateWrapper.eq("id",id);
        boolean byId = gBrandService.update(updateWrapper);
        return new Result(byId);
    }
}

