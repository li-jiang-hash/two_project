package com.aaa.group_three.controller;


import com.aaa.entity.BBusinessInfo;
import com.aaa.entity.EEmpInfo;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.group_three.service.impl.EEmpInfoServiceImpl;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    @Resource
    private EEmpInfoServiceImpl empInfoService;

    @PostMapping("ruzhu")
    public Result getAllApp(PageInfo page, @RequestBody BBusinessInfo bBusinessInfo){
        Page page1 = bBusinessInfoService.getPageData(page, bBusinessInfo);
        return new Result(page1);
    }
    //商家审核
    @PostMapping("shenhe")
    public Result getById(@RequestBody BBusinessInfo bBusinessInfo){
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb = " + bBusinessInfo);

        UpdateWrapper<BBusinessInfo> wrapper=new UpdateWrapper<>();
        wrapper.set("status",bBusinessInfo.getStatus());
        wrapper.set("reason",bBusinessInfo.getReason());
        wrapper.eq("id",bBusinessInfo.getId());

        //审核完成后把数据插入商家表
        EEmpInfo empInfo = new EEmpInfo();
        empInfo.setTelephone(bBusinessInfo.getTelephone());
        empInfo.setPassword(new BCryptPasswordEncoder().encode(bBusinessInfo.getPassword()));
        empInfo.setEname(bBusinessInfo.getSname());
        empInfo.setGmtCreate(LocalDateTime.now());
        empInfoService.save(empInfo);



        boolean byId = bBusinessInfoService.update(wrapper);
        return new Result(byId);
    }

    //查询店铺
    //首页店铺与名字
    @GetMapping("getsname")
    public Result getBid(BBusinessInfo bBusinessInfo){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        if (bBusinessInfo.getId() != null){
            queryWrapper.eq("id",bBusinessInfo.getId());
        }
        queryWrapper.select("sname","id","bicon");
        return new Result<>(bBusinessInfoService.list(queryWrapper));
    }
    //根据手机号查询该用户是否为商家
    @GetMapping("tokenphone")
    public Result getPhone(String phone){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("telephone",phone);
        boolean one = bBusinessInfoService.getOne(queryWrapper) ==null;
        return new Result(one);
    }
    @PostMapping("/storeinsert")
    public Result storeinsert(@RequestBody BBusinessInfo bBusinessInfo){
        boolean save = bBusinessInfoService.save(bBusinessInfo);
        return new Result(save);
    }


}
