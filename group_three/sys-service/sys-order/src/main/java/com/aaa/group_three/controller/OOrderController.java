package com.aaa.group_three.controller;


import com.aaa.entity.OOrder;
import com.aaa.group_three.service.IOOrderService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/o-order")
public class OOrderController {

    @Resource
    private IOOrderService orderService;

    /**
     * 查询用户是否买过这个商品
     * @param id goods_id
     * @return
     */
    @GetMapping("hasBuyThisGoods/{id}/{userid}")
    public Result hasBuyThisGoods(@PathVariable String id, @PathVariable String userid){
        QueryWrapper<OOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("goodsid", id);
        wrapper.eq("uid",userid);
        return new Result<>(orderService.list(wrapper).size() != 0);
    }

    @GetMapping("findOrder/{pageCurrent}/{pageSize}")
    public Result findOrder(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize,String zhuangtai,String userId){
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = orderService.findOrder(pageInfo,userId);
        return new Result<>(page);
    }

}

