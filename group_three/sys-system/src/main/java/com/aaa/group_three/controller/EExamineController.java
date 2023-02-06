package com.aaa.group_three.controller;


import com.aaa.entity.EExamine;
import com.aaa.group_three.service.impl.EExamineServiceImpl;
import com.aaa.util.Result;
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
    // 查询所有的申诉信息
//    @GetMapping()
//    public Result getAllApp(Integer curr,Integer size, EExamine eExamine){
//        Page page=new Page(curr,size);
//        Page pageData = eExamineService.getAll(page, eExamine);
//        return new Result(pageData);
//    }
//    @PostMapping()
//    public Result add(@RequestBody EExamine eExamine){
//        boolean b = eExamineService.saveOrUpdate(eExamine);
//        return new Result(b);
//    }
}

