package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.ERole;
import com.yyl.systemdemo.service.IERoleService;
import com.yyl.util.PageInfo;
import com.yyl.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

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
    public Result getAllRole(PageInfo page, ERole role){


        Page page1 = roleService.getPageData(page, role);
        return new Result(page1);
    }


    /**
     * 添加角色信息
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public Result addRole(ERole role){
        //saveOrUpdate  添加或修改
        //role  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(roleService.saveOrUpdate(role));
    }

    /**
     * 修改角色信息
     */
    @PostMapping("upStatus/{id}/{status}")
    public Result updRole(@PathVariable Integer id,@PathVariable String status){
        ERole role=new ERole();
        role.setStatus(status);
        role.setId(id);
        return new Result(roleService.updateById(role));
    }


    /**
     * 删除角色信息
     */
    @DeleteMapping("{id}")
    public Result delRole(@PathVariable Integer id){
        return new Result(roleService.removeById(id));
    }

    /**
     * 权限分配的时候 使用的数据
     */
    @PostMapping("selectPermission/{id}")
    public Result selPermission(@PathVariable Integer id){
        return new Result(roleService.getPermissionData(id));
    }
}

