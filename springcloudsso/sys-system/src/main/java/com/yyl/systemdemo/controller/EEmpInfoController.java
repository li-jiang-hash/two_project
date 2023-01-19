package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EEmpInfo;
import com.yyl.systemdemo.service.IEEmpInfoService;
import com.yyl.util.PageInfo;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 管理员信息表 前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
@RestController
@RequestMapping("/e-emp-info")
public class EEmpInfoController {
    @Resource
    private IEEmpInfoService empInfoService;

    // 查询所有的emp
    @PostMapping
    public Result getAllEmp(PageInfo page, EEmpInfo empInfo){


        Page page1 = empInfoService.getPageData(page, empInfo);
        return new Result(page1);
    }


    // 根据员工的id 查询员工的角色
    @GetMapping("{id}")
    public List<EEmpInfo> findByUid(@PathVariable Integer id){

        return empInfoService.getById(id);
    }

    /**
     * 添加角色信息
     * @param empInfo
     * @return
     */
    @PostMapping("insertEmp")
    public Result addEmp(EEmpInfo empInfo){
        //创建/修改时间
        LocalDateTime dt = LocalDateTime.now ();
//        empInfo.setGmtCreate(dt);
        empInfo.setGmtModified(dt);
        //saveOrUpdate  添加或修改
        //role  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(empInfoService.saveOrUpdate(empInfo));
    }


    /**
     * 删除角色信息
     */
    @DeleteMapping("{id}")
    public Result delEmp(@PathVariable Integer id){
        return new Result(empInfoService.removeById(id));
    }

}

