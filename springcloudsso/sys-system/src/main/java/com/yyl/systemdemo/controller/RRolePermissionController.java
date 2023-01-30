package com.yyl.systemdemo.controller;


import com.yyl.systemdemo.service.IRRolePermissionService;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 角色权限连表 前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/r-role-permission")
public class RRolePermissionController {

    @Resource
    private IRRolePermissionService rolePermissionService;

    @PostMapping("{roleId}")
    public Result updPer(@PathVariable String roleId,String menuids){
        return new Result(rolePermissionService.updPermission(roleId,menuids));
    }

}

