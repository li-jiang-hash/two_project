package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.ERole;
import com.yyl.systemdemo.service.IERoleService;
import com.yyl.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/e-role")
public class ERoleController {

    @Resource
    private IERoleService roleService;

    // 查询所有的role
    @PostMapping
    public Result getAllRole(Integer currentPage, Integer pageSize, ERole role){

        System.out.println("role的值："+role);
        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(role.getStatus())){
            queryWrapper.eq("status",role.getStatus());
        }
        // "" null
       if(StringUtils.isNotBlank(role.getRoleName())){
           queryWrapper.like("role_name",role.getRoleName());
       }

        Page page1 = roleService.page(page, queryWrapper);
        return new Result(page1);
    }

}

