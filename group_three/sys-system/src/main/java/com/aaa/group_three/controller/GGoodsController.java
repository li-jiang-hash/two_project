package com.aaa.group_three.controller;


import com.aaa.entity.GGoods;
import com.aaa.entity.OStock;
import com.aaa.group_three.service.IGGoodsService;
import com.aaa.group_three.service.IOStockService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * 店铺商品查询
     *
     * @return
     */
    @GetMapping("findGoodsByBid/{pageCurrent}/{pageSize}/{id}")
    public Result findAll(@PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable String id) {
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = goodsService.getGoodsAll(pageInfo, id);
        return new Result(page);
    }

    /**
     * 商品购买显示
     *
     * @return
     */
    @GetMapping("findGoodsByGoodsid/{id}")
    public Result findGoodsByGoodsid(@PathVariable Integer id) {
        Map<String, Object> map = goodsService.getGoodsByGoodsid(id);
        return new Result<>(map);
    }

    //    查询goods表中数据
    @PostMapping("findAllGoods")
    public Result getAllApp(PageInfo pageInfo, GGoods goods) {
        Page allGoods = goodsService.getAllGoods(pageInfo, goods);
        return new Result(allGoods);
    }

    //    商品管理审核操作
    @PostMapping("shenhe")
    public Result getId(@RequestBody GGoods goods) {
        return new Result(goodsService.updateById(goods));
    }

    //查询所有商品
    @PostMapping("selectAllGoodsInfoByBusinessId/{currentPage}/{pageSize}/{id}")
    public Result getAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize, @PathVariable String id, @RequestBody GGoods goods) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid", id);
        System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG = " + goods);
        queryWrapper.like(goods.getGname() != null, "gname", goods.getGname());
        queryWrapper.like(goods.getState() != null, "state", goods.getState());
        queryWrapper.like(goods.getStatus() != null, "status", goods.getStatus());

        List list = goodsService.list(queryWrapper);
        System.out.println("list = " + list);
        IPage page = new Page(currentPage, pageSize);
        page.setRecords(list);
        page.setTotal(list.size());
        return new Result(page);
    }

    @Resource
    private IOStockService ioStockService;

    //添加商品
    @PostMapping("addGoods")
    public Result AddGoods(@RequestBody GGoods goods) {
        System.out.println("gggggggggggggggggggggggg = " + goods);
        goods.setState(2);
        goods.setIsdeleted(0);
        goods.setAddtime(LocalDateTime.now());
        boolean save = goodsService.save(goods);
        OStock oStock = new OStock();
        oStock.setPrice(goods.getPrice());
        oStock.setTotalNum(goods.getTotalnum());
        oStock.setGoodsId(goods.getId());
        ioStockService.save(oStock);
        return new Result();
    }

}

