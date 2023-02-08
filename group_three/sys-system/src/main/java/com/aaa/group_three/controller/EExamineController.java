package com.aaa.group_three.controller;


import com.aaa.entity.BAppeal;
import com.aaa.entity.EExamine;
import com.aaa.group_three.service.impl.EExamineServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
//     查询表中数据
    @GetMapping("examine")
    public Result getAllApp(PageInfo pageInfo,String startTime, String endTime){
       Page page=eExamineService.getAll(pageInfo,startTime,endTime);

        return new Result(page);
    }
//
//新增数据
    @PostMapping("insert")
    public Result add(@RequestBody EExamine eExamine){
        String dateFormat="yyyy-MM-dd HH:mm:ss";
        LocalDateTime time =LocalDateTime.now();
        eExamine.setGmtCreate(time);
        eExamine.setCheckman("1");
        boolean b = eExamineService.saveOrUpdate(eExamine);
        return new Result(b);
    }
//    逻辑删除，修改存在状态
    @PostMapping("{id}")
    public Result del(@PathVariable String id){
        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("is_deleted","1");
        updateWrapper.eq("id",id);
        boolean byId = eExamineService.update(updateWrapper);
        return new Result(byId);
    }

}

