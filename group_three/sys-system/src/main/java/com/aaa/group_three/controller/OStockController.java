package com.aaa.group_three.controller;


import com.aaa.entity.OStock;
import com.aaa.group_three.service.IOStockService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商品存货信息 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/o-stock")
public class OStockController {

    @Resource
    private IOStockService stockService;

    /**
     * 获取商品交易数量
     * @param id goods_id
     * @return
     */
    @GetMapping("getGoodsSellNum/{id}")
    public Result getGoodsSellNum(@PathVariable Integer id){
        QueryWrapper<OStock> wrapper = new QueryWrapper<>();
        wrapper.select("sell_num");
        wrapper.eq("goods_id",id);
        return new Result<>(stockService.list(wrapper));
    }
}

