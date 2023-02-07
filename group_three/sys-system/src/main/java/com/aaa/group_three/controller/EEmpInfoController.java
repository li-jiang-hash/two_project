package com.aaa.group_three.controller;


import com.aaa.entity.EEmpInfo;
import com.aaa.group_three.service.IEEmpInfoService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


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

