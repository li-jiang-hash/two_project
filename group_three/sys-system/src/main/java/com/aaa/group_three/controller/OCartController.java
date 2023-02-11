package com.aaa.group_three.controller;


import com.aaa.entity.OCart;
import com.aaa.group_three.service.IOCartService;
import com.aaa.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取用户收货地址
     * @return
     */
    @PostMapping("addCart")
    public Result addCart(@RequestBody OCart oCart){
        return new Result<>(cartService.saveOrUpdate(oCart));
    }

}

