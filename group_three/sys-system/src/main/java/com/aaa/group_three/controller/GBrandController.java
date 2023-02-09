package com.aaa.group_three.controller;


import com.aaa.entity.GBrand;
import com.aaa.entity.GGoods;
import com.aaa.group_three.service.impl.GBrandServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    //    查询goods表中数据
    @PostMapping("findAllBrands")
    public Result getAllApp(PageInfo pageInfo,GBrand gBrand){
        Page list = gBrandService.getAllBrands(pageInfo, gBrand);
        return new Result(list);
    }

}

