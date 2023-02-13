package com.aaa.group_three.controller;


import com.aaa.entity.OCart;
import com.aaa.group_three.service.IOCartService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-11
 */
@RestController
@RequestMapping("/o-cart")
public class OCartController {

    @Resource
    private IOCartService cartService;

    /**
     * 加入购物车
     * @return
     */
    @PostMapping("addCart")
    public Result addCart(@RequestBody OCart oCart){
        return new Result<>(cartService.saveOrUpdate(oCart));
    }

    /**
     * 查询购物车
     * @return
     */
    @GetMapping("findCart")
    public Result findCart(Integer currentPage, Integer pageSize,String userId){
        PageInfo pageInfo = new PageInfo(currentPage, pageSize);
        Page page = cartService.findCart(userId,pageInfo);
        return new Result<>(page);
    }

    /**
     * 删除购物车
     * @return
     */
    @GetMapping("deleteCart")
    public Result deleteCart(String[] id){
        boolean update = false;
        for (String s : id) {
            UpdateWrapper<OCart> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",s);
            wrapper.set("is_deleted",1);
            update = cartService.update(wrapper);
        }
        return new Result<>(update);
    }

}

