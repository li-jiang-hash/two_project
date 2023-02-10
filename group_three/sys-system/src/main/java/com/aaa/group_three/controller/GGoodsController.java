package com.aaa.group_three.controller;


import com.aaa.entity.GGoods;
import com.aaa.group_three.service.IGGoodsService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/g-goods")
public class GGoodsController {
    @Resource
    private IGGoodsService goodsService;

    @GetMapping("findGoodsByBid/{pageCurrent}/{pageSize}/{id}")
    public Result findAll(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable String id) {
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = goodsService.getGoodsAll(pageInfo, id);
        return new Result(page);
    }

//    查询goods表中数据
    @PostMapping("findAllGoods")
    public Result getAllApp(PageInfo pageInfo, GGoods goods){
        Page allGoods = goodsService.getAllGoods(pageInfo, goods);
        return new Result(allGoods);
    }
//    商品管理审核操作
    @PostMapping("shenhe")
    public Result getId(@RequestBody GGoods goods){
        return new Result(goodsService.updateById(goods));
    }

}

