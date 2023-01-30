package com.aaa.group_three.controller;


import com.aaa.group_three.service.IRRolePermissionService;
import com.aaa.util.Result;
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
    public Result updPer(@PathVariable String roleId, String menuids){
        return new Result(rolePermissionService.updPermission(roleId,menuids));
    }

}

