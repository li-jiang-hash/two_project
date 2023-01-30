package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EEmpInfo;
import com.yyl.entity.TbHeadNav;
import com.yyl.systemdemo.service.ITbHeadNavService;
import com.yyl.util.PageInfo;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/tb-head-nav")
public class TbHeadNavController {

    @Resource
    private ITbHeadNavService headNavService;


    // 查询所有的emp
    @PostMapping
    public Result getAllEmp(PageInfo page, TbHeadNav headNav){


        Page page1 = headNavService.getPageData(page, headNav);
        return new Result(page1);
    }


    /**
     * 添加角色信息
     * @param headNav
     * @return
     */
    @PostMapping("insertEmp")
    public Result addEmp(TbHeadNav headNav){
        //saveOrUpdate  添加或修改
        //role  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(headNavService.saveOrUpdate(headNav));
    }


}

