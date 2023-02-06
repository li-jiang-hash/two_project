package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.EExamine;
import com.aaa.group_three.service.impl.BAppealServiceImpl;
import com.aaa.group_three.service.impl.EExamineServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    // 查询所有的申诉信息
    @PostMapping("examine")
    public Result getAllApp(PageInfo page, EExamine eExamine){
        IPage page1 = eExamineService.getPageData(page, eExamine);
        System.out.println("page1 = " + page1);
        return new Result(page1);
    }
}

