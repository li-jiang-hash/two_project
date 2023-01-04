package com.yyl.systemdemo.controller;


import com.yyl.systemdemo.service.IEPermissionService;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 左侧菜单地址 前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@RestController
@RequestMapping("/e-permission")
public class EPermissionController {
    // 查询所有的菜单的信息   对应好层级关系
    // [{ children=[{ children=[{}]}]},{},{}]
    @Resource
    private IEPermissionService permissionService;
    @GetMapping("leftMenus")
    public Result leftMenus(){
       return new Result(permissionService.getAllMenus());
    }


}

