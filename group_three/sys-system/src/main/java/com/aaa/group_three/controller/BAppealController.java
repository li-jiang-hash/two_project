package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.entity.EEmpInfo;
import com.aaa.group_three.service.IEEmpInfoService;
import com.aaa.group_three.service.impl.BAppealServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家申诉表 前端控制器
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/b-appeal")
public class BAppealController {
    @Resource
    private BAppealServiceImpl bAppealService;
    // 查询所有的申诉信息
    @PostMapping("appeal")
    public Result getAllApp(PageInfo page,@RequestBody BAppeal bAppeal){
        Page page1 = (Page) bAppealService.getPageData(page, bAppeal);
        return new Result(page1);
    }
    @PostMapping("tongguo")
    public Result getById(BAppeal bAppeal){
        QueryWrapper<BAppeal> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",bAppeal.getId());
        boolean byId = bAppealService.update(bAppeal,queryWrapper);
        return new Result(byId);
    }
}

