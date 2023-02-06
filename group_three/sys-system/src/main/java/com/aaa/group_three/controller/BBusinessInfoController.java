package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.group_three.service.impl.BAppealServiceImpl;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商家信息表 前端控制器
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/b-business-info")
public class BBusinessInfoController {
    @Resource
    private BBusinessInfoServiceImpl bBusinessInfoService;
    @PostMapping("ruzhu")
    public Result getAllApp(PageInfo page, BBusinessInfo bBusinessInfo){
        IPage page1 = bBusinessInfoService.getPageData(page, bBusinessInfo);
        return new Result(page1);
    }

}

