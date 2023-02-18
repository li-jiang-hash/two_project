package com.aaa.group_three.controller;


import com.aaa.group_three.service.IGUnitService;
import com.aaa.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 商品计量单位 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/g-unit")
public class GUnitController {
    @Resource
    private IGUnitService igUnitService;

    @GetMapping("selectAllUnit")
    public Result findGoodsByGoodsid(){
        return new Result<>(igUnitService.list());
    }
}

