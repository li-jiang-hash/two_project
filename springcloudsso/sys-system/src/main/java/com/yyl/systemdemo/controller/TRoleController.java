package com.yyl.systemdemo.controller;


import com.yyl.entity.TRole;
import com.yyl.systemdemo.service.ITRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@RestController
@RequestMapping("/t-role")
public class TRoleController {

    @Resource
    private ITRoleService roleService;

    // 根据用户的id 查询用户的角色
    @GetMapping("{uid}")
    public List<TRole> findByUid(@PathVariable Integer uid){
        return roleService.getByUid(uid);
    }

}

