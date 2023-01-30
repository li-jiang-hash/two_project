package com.aaa.group_three.controller;

import com.aaa.entity.ERole;
import com.aaa.group_three.service.IERoleService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:47:20
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping({"/e-role"})
public class ERoleController {
    @Resource
    private IERoleService roleService;

    public ERoleController() {
    }

    @PostMapping
    public Result getAllRole(Integer currentPage, Integer pageSize, ERole role) {
        System.out.println("role的值：" + role);
        Page page = new Page((long)currentPage, (long)pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(role.getStatus())) {
            queryWrapper.eq("status", role.getStatus());
        }

        if (StringUtils.isNotBlank(role.getRoleName())) {
            queryWrapper.like("role_name", role.getRoleName());
        }

        Page page1 = (Page)this.roleService.page(page, queryWrapper);
        return new Result(page1);
    }
}
