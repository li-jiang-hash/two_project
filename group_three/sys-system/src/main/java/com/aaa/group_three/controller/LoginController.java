package com.aaa.group_three.controller;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.ERole;
import com.aaa.entity.REmpRole;
import com.aaa.group_three.service.IEEmpInfoService;
import com.aaa.group_three.service.IERoleService;
import com.aaa.group_three.service.IREmpRoleService;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private IEEmpInfoService empInfoService;
    @Resource
    private IREmpRoleService empRoleService;
    @Resource
    private BBusinessInfoServiceImpl bBusinessInfoService;
    @Resource
    private IERoleService roleService;

    // 根据电话号码查询用户的信息

    @GetMapping("/getUserByName/{username}")
    public EEmpInfo getByUserName(@PathVariable String username) {
        System.out.println("username = " + username);
        QueryWrapper<EEmpInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("telephone", username);
        EEmpInfo one = empInfoService.getOne(queryWrapper);
        return one;
    }

    /**
     * 根据用户的id查询用户的角色的信息
     */
    @GetMapping("/{eid}")
    public List<String> getRoleList(@PathVariable String eid) {

        // 根据用户的id查询用户的角色信息
        // 1. 根据用户的id查询用户的角色的id
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("emp_id", eid);
        List<REmpRole> list = empRoleService.list(queryWrapper);
        List<String> roleList = new ArrayList<>();

        // 2. 根据角色的id查询角色的信息
        for (REmpRole rEmpRole : list) {
            ERole byId = roleService.getById(rEmpRole.getRoleId());
            roleList.add("ROLE_" + byId.getRoleName());
        }
        return roleList;
    }
    @GetMapping
    public Result getUser(String telephone) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone);
        return new Result(empInfoService.getOne(queryWrapper));
    }

}
