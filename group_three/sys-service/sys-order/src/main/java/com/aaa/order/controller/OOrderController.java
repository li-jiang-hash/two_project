package com.aaa.order.controller;


import com.aaa.entity.OOrder;
import com.aaa.order.service.IOOrderService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    /**
     * 查询订单
     * @param zhuangtai state
     * @return
     */
    @GetMapping("findOrder/{pageCurrent}/{pageSize}")
    public Result findOrder(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize,Integer zhuangtai,String userId){
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = orderService.findOrder(pageInfo,userId,zhuangtai);
        return new Result<>(page);
    }

    /**
     * 添加订单
     * @return
     */
    @PostMapping("settlement")
    public Result settlement(@RequestBody List<OOrder> orderList){
        for (OOrder oOrder : orderList) {
            oOrder.setCode(UUID.randomUUID().toString());
            oOrder.setOrderdate(LocalDateTime.now());
        }
        return new Result<>(orderService.saveOrUpdateBatch(orderList),"添加订单成功！");
    }
    @PostMapping("settlementOne")
    public Result settlement(@RequestBody OOrder order){
        System.out.println("order = " + order);
        String s = UUID.randomUUID().toString();
        order.setCode(s);
        order.setOrderdate(LocalDateTime.now());
        boolean b = orderService.saveOrUpdate(order);
        Result result=new Result<>();
        if (b){
            System.out.println("order.getCode() = " + s);
            result.setData(order);
            result.setMsg("添加订单成功了");
            return result;
        }
        return new Result<>(null);
    }
}

