package com.aaa.group_three.controller;


import com.aaa.entity.UUserInfo;
import com.aaa.group_three.service.IUUserInfoService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:51:23
 * @Description:
 * @Since version:  2022.10
 */
//@CrossOrigin
@RestController
@RequestMapping("/user")
public class UUserInfoController {

    @Resource
    private IUUserInfoService userInfoService;
    //根据用户名获取用户信息
    @PostMapping("getUserByName/{username}")
    public UUserInfo getUser(@PathVariable String username){
        QueryWrapper<UUserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uname",username);
        return userInfoService.getOne(queryWrapper);
    }
    //根据用户手机号获取信息
    @GetMapping("getUserByPhone/{telephone}")
    public UUserInfo getUserPhone(@PathVariable String telephone){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("telephone",telephone);
        UUserInfo one = userInfoService.getOne(queryWrapper);
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
    public Result updateMessage(@RequestBody UUserInfo uUserInfo){
        uUserInfo.setUpdatetime(LocalDateTime.now());
        uUserInfo.setUpdateman(uUserInfo.getUname());
        boolean b = userInfoService.updateById(uUserInfo);
        Result result = new Result();
        if(b){
            result.setMsg("修改用户信息成功");
            return result;
        }else {
            result.setMsg("系统繁忙，请稍后重新尝试");
            return result;
        }
    }

//    查询会员列表信息
    @GetMapping("getAll")
    public Result getAll(PageInfo pageInfo, String startTime, String endTime){
        Page page=userInfoService.getAll(pageInfo,startTime,endTime);
        return new Result(page);
    }
    //改变状态
    @PostMapping("update")
    public Result update(@RequestParam String id,@RequestParam Integer status){
        UpdateWrapper updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("status",status);
        updateWrapper.eq("id",id);
        return new Result(userInfoService.update(updateWrapper));
    }
    //个人信息
    @GetMapping("/findUserByPhone")
    public Result getUserByPhone(String phone){
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("telephone",phone);
        UUserInfo one = userInfoService.getOne(queryWrapper);
        return new Result(one);
    }

    /**
     * 获取userid
     * @return
     */
    @GetMapping("getUserid/{telephone}")
    public Result getUserid(@PathVariable String telephone){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("telephone",telephone);
        queryWrapper.select("id");
        return new Result<>(userInfoService.list(queryWrapper).get(0));
    }

    @PostMapping("/updatePassword/{phone}/{newPassword}")
    public Result UpdateEmp(@PathVariable String phone,@PathVariable String newPassword){
        System.out.println("phone = " + phone);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",phone);
        UUserInfo one = userInfoService.getOne(queryWrapper);
        one.setUpdatetime(LocalDateTime.now ());
        one.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        boolean b = userInfoService.updateById(one);
        Result result = new Result();
        if(b){
            result.setMsg("密码修改成功，请您重新登录");
            return result;
        }else {
            result.setMsg("系统繁忙，请稍后重新尝试");
            return result;
        }
        //saveOrUpdate  添加或修改
        //role  对象有id的值的时候 修改
        //id没有值的时候 添加
    }


}

