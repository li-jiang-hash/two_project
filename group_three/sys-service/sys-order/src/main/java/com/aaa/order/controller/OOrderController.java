package com.aaa.order.controller;


import com.aaa.entity.GGoods;
import com.aaa.entity.OOrder;
import com.aaa.entity.Order;
import com.aaa.order.service.IGGoodsService;
import com.aaa.order.service.IOOrderService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        IPage<OOrder> page = orderService.findOrder(pageCurrent,pageSize,userId,zhuangtai);
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

    /**
     * 删除/取消/失效订单
     * @return
     */
    @GetMapping("deleteOrder/{id}/{state}")
    public Result deleteOrder(@PathVariable Integer id, @PathVariable Integer state){
        UpdateWrapper<OOrder> wrapper = new UpdateWrapper<>();
        wrapper.set("state",state);
        wrapper.eq("id",id);
        return new Result<>(orderService.update(wrapper));
    }

    @PostMapping("settlementOne")
    public Result settlement(@RequestBody OOrder order){
        String s = UUID.randomUUID().toString();
        order.setCode(s);
        order.setOrderdate(LocalDateTime.now());
        boolean b = orderService.saveOrUpdate(order);
        Result result=new Result<>();
        if (b){
            result.setData(order);
            result.setMsg("添加订单成功了");
            return result;
        }
        return new Result<>(null);
    }

@Resource
private IGGoodsService igGoodsService;

    //订单管理查询订单
    @PostMapping("selectAll/{bid}/{pageCurrent}/{pageSize}")
    public Result GoodsOrder(@PathVariable String bid,@PathVariable Integer pageCurrent, @PathVariable Integer pageSize,@RequestBody OOrder order){
//        orderService.list();
        QueryWrapper queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("gname",order.getGname());
        queryWrapper1.select("id");
        GGoods one = igGoodsService.getOne(queryWrapper1);
        System.out.println("one = " + one);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid", bid);
        queryWrapper.like(order.getSend() != null ,"send",order.getSend());
        if(one !=null) queryWrapper.like("goodsid",one.getId());
        List list = orderService.list(queryWrapper);
        IPage page = new Page(pageCurrent, pageSize);
        page.setRecords(list);
        page.setTotal(list.size());
        return new Result<>(page);
    }
    //更新订单状态
    @PostMapping("updateSend/{id}/{code}")
    public Result UpOrder(@PathVariable Integer id,@PathVariable String code){
        OOrder order = new OOrder();
        order.setId(id);
        order.setCode(code);
        order.setSend(1);
        orderService.updateById(order);
        Result result=new Result<>();
        if (true){
            result.setMsg("修改订单成功了");
            return result;
        }
        return new Result<>(null);
    }
}

