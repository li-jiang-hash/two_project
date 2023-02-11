package com.aaa.group_three.controller;


import com.aaa.entity.UCollection;
import com.aaa.group_three.service.IUCollectionService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/u-collection")
public class UCollectionController {

    @Resource
    private IUCollectionService collectionService;

    /**
     * 查询商品\店铺收藏状态
     * @param id goods_id
     * @param userId user_id
     * @return
     */
    @GetMapping("isGoodsCollection")
    public Result isGoodsCollection(String bid, String id, String userId) {
        QueryWrapper<UCollection> wrapper = new QueryWrapper<>();
        wrapper.select("status");
        wrapper.eq("uid", userId);
        if (id != null) {
            wrapper.eq("goodsid",id);
        } else {
            wrapper.eq("bid",bid);
        }
        return new Result<>(collectionService.list(wrapper));
    }

    /**
     * 修改收藏状态
     * @param id goods_id
     * @param status 收藏状态
     * @return
     */
    @PostMapping("changeCollectionStatus")
    public Result changeCollection(String bid, String id, Integer status, String userId){
        UpdateWrapper<UCollection> wrapper = new UpdateWrapper<>();
        wrapper.set("status",status);
        wrapper.eq("uid", userId);
        if (id != null) {
            wrapper.eq("goodsid",id);
        } else {
            wrapper.eq("bid",bid);
        }
        return new Result<>(collectionService.update(wrapper));
    }

}
