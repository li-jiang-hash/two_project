package com.aaa.group_three.controller;

import com.aaa.group_three.service.IEPermissionService;
import com.aaa.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:46:30
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping({"/e-permission"})
public class EPermissionController {
    @Resource
    private IEPermissionService permissionService;


    @GetMapping({"leftMenus"})
    public Result leftMenus(String r) {
        System.out.println("r = " + r);
        return new Result(permissionService.getAllMenus(r));
    }
}
