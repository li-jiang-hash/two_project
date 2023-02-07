package com.aaa.group_three.controller;


import com.aaa.entity.UUserInfo;
import com.aaa.group_three.service.IUUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:51:23
 * @Description:
 * @Since version:  2022.10
 */
//@CrossOrigin
@RestController
@RequestMapping("/sys-sso")
public class UUserInfoController {

    @Resource
    private IUUserInfoService userInfoService;
    //根据用户名获取用户信息
    @PostMapping("getUserByName/{username}")
    public UUserInfo getUser(@PathVariable String username){
        QueryWrapper<UUserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uname",username);
        System.out.println(queryWrapper);
        return userInfoService.getOne(queryWrapper);
    }
    //根据用户手机号获取信息
    @GetMapping("getUserByPhone/{telephone}")
    public UUserInfo getUserPhone(@PathVariable String telephone){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone);
        UUserInfo one = userInfoService.getOne(queryWrapper);
        System.out.println("one = " + one);
        return one;
    }
    //添加
    @PostMapping("/insertAddr")
    public boolean insertAddr(@RequestBody UUserInfo uUserInfo){
        return userInfoService.save(uUserInfo);
    }
    //删除
    @GetMapping("/deleteById/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return userInfoService.removeById(id);
    }
    //全查
    @GetMapping("findAll")
    public List<UUserInfo> findAll(){
        return userInfoService.list();
    }
    //修改
    @PostMapping("updateMessage")
    public boolean updateMessage(@RequestBody UUserInfo uUserInfo){
        return userInfoService.updateById(uUserInfo);
    }
}

