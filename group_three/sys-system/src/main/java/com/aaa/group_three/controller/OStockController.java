package com.aaa.group_three.controller;


import com.aaa.entity.OStock;
import com.aaa.group_three.service.IGGoodsService;
import com.aaa.group_three.service.IOStockService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private IGGoodsService goodsService;

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

    /**
     * 获取商品剩余库存
     * @return
     */
    @GetMapping("getStock/{id}")
    public Result getStock(@PathVariable String id){
        QueryWrapper<OStock> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",id);
        wrapper.select("residue");
        return new Result(stockService.list(wrapper).get(0).getResidue());
    }

    /**
     * 减去商品库存
     * @return
     */
    @PostMapping("subtractResidue")
    public Result subtractResidue(Integer[] goodsIds,Integer[] nums){
        for (int i = 0; i < goodsIds.length; i++) {
            //获取商品库存
            QueryWrapper<OStock> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("goods_id",goodsIds[i]);
            Integer residue = stockService.list(queryWrapper).get(0).getResidue();
            residue = residue - nums[i];
            //判断商品库存是否足够
            if (residue < 0) {
                String gname = goodsService.getById(goodsIds[i]).getGname();
                return new Result<>( false,gname + "库存不足，添加订单失败");
            }
            //设置剩余库存
            UpdateWrapper<OStock> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("residue",residue);
            updateWrapper.eq("goods_id",goodsIds[i]);
            stockService.update(updateWrapper);
        }
        return new Result<>(true);
    }

    /**
     * 订单取消/失效 库存恢复
     * @return
     */
    @PostMapping("regainResidue")
    public Result regainResidue(Integer goodsId,Integer num){
        QueryWrapper<OStock> wrapper = new QueryWrapper<>();
        //查剩余库存
        wrapper.eq("goods_id",goodsId);
        Integer residue = stockService.getOne(wrapper).getResidue();
        //设置剩余库存
        residue = num + residue;
        UpdateWrapper<OStock> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("goods_id",goodsId);
        wrapper1.set("residue",residue);
        return new Result<>(stockService.update(wrapper1));
    }
}

