package com.aaa.group_three.controller;


import com.aaa.entity.BBusinessInfo;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商家信息表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/b-business-info")
public class BBusinessInfoController {
    @Resource
    private BBusinessInfoServiceImpl bBusinessInfoService;

    @PostMapping("ruzhu")
    public Result getAllApp(PageInfo page, @RequestBody BBusinessInfo bBusinessInfo){
        Page page1 = bBusinessInfoService.getPageData(page, bBusinessInfo);
        return new Result(page1);
    }
    @PostMapping("shenhe")
    public Result getById(@RequestBody BBusinessInfo bBusinessInfo){
        QueryWrapper<BBusinessInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",bBusinessInfo.getStatus());
        boolean byId = bBusinessInfoService.update(bBusinessInfo,queryWrapper);
        return new Result(byId);
    }

    //    查询店铺名称
    @GetMapping("getsname")
    public Result getSname(){
        return new Result(bBusinessInfoService.list());
    }
    //根据手机号查询该用户是否为商家
    @GetMapping("tokenphone")
    public Result getPhone(String phone){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("telephone",phone);
        boolean one = bBusinessInfoService.getOne(queryWrapper) ==null;
        return new Result(one);
    }
<<<<<<< HEAD
    @PostMapping("/storeinsert")
    public Result storeinsert(@RequestBody BBusinessInfo bBusinessInfo){
        System.out.println("1212122121111111111111111111111111111111111111111111111 = " + bBusinessInfo);
        boolean save = bBusinessInfoService.save(bBusinessInfo);
        return new Result(save);
    }


=======
>>>>>>> e50bef7b2b604e67cb3e44fd0051c0b5508b8892
}
