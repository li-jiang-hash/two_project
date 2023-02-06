package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.EExamine;
import com.aaa.group_three.service.impl.EExamineServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 审查表 前端控制器
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/e-examine")
public class EExamineController {
    @Resource
    private EExamineServiceImpl eExamineService;
//     查询所有的申诉信息
    @GetMapping("examine")
    public Result getAllApp(PageInfo pageInfo, @RequestBody EExamine eExamine){
       Page page=eExamineService.getAll(pageInfo,eExamine);
        return new Result(page);
    }
    @PostMapping("shenhe")
    public Result getById(EExamine eExamine){
        QueryWrapper<EExamine> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",eExamine.getId());
        boolean byId = eExamineService.update(eExamine,queryWrapper);
        return new Result(byId);
    }
}

