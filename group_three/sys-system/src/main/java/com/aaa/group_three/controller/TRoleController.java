package com.aaa.group_three.controller;

import com.aaa.entity.TRole;
import com.aaa.group_three.service.ITRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:50:00
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping({"/t-role"})
public class TRoleController {
    @Resource
    private ITRoleService roleService;


    @GetMapping({"{uid}"})
    public List<TRole> findByUid(@PathVariable Integer uid) {
        return roleService.getByUid(uid);
    }



}
