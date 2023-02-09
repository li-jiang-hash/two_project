package com.aaa.group_three.controller;


import com.aaa.group_three.service.IGGoodsService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     *店铺商品
     * @return
     */
    @GetMapping("findGoodsByBid/{pageCurrent}/{pageSize}/{id}")
    public Result findAll(@PathVariable Integer pageCurrent,@PathVariable Integer pageSize,@PathVariable String id){
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = goodsService.getGoodsAll(pageInfo,id);
        return new Result(page);
    }

}

