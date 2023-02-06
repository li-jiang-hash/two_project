package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.group_three.service.impl.BAppealServiceImpl;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

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
    public Result getAllApp(PageInfo page, @RequestBody BBusinessInfo bBusinessInfo){
        Page page1 = bBusinessInfoService.getPageData(page, bBusinessInfo);
        return new Result(page1);
    }
    @PostMapping("shenhe")
    public Result getById(@RequestBody BBusinessInfo bBusinessInfo){
        QueryWrapper<BBusinessInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",bBusinessInfo.getStatus());
        boolean byId = bBusinessInfoService.update(bBusinessInfo,queryWrapper);
        return new Result(byId);
    }

}

