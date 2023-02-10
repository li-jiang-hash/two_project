package com.aaa.group_three.controller;


import com.aaa.group_three.service.IUCommentService;
import com.aaa.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    private IUCommentService commentService;

    @GetMapping("isGoodsCollection")
    public Result isGoodsCollection() {
        return null;
    }

}

