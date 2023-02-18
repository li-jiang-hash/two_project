package com.aaa.group_three.controller;

import com.aaa.entity.BBusinessInfo;
import com.aaa.entity.EEmpInfo;
import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.service.impl.BBusinessInfoServiceImpl;
import com.aaa.group_three.service.impl.EEmpInfoServiceImpl;
import com.aaa.group_three.service.impl.TbZoneBusinessServiceImpl;
import com.aaa.util.DateUtil;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

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
    private TbZoneBusinessServiceImpl tbZoneBusinessService;

    //查询入住审核
    @PostMapping("ruzhu")
    public Result getAllApp(PageInfo page, @RequestBody BBusinessInfo bBusinessInfo) {
        Page page1 = bBusinessInfoService.getPageData(page, bBusinessInfo);
        return new Result(page1);
    }

    //审核操作
    @PostMapping("shenhe")
    public Result getById(@RequestBody BBusinessInfo bBusinessInfo) {
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.set("status", bBusinessInfo.getStatus());
        wrapper.set("reason", bBusinessInfo.getReason());
        wrapper.eq("id", bBusinessInfo.getId());
        boolean byId = bBusinessInfoService.update(wrapper);
    //审核操作时添加专区表数据
        TbZoneBusiness tbZoneBusiness=new TbZoneBusiness();
        tbZoneBusiness.setZoneId(bBusinessInfo.getSortid());
        tbZoneBusiness.setGmtCreate(LocalDateTime.now());
        tbZoneBusiness.setBid(bBusinessInfo.getId());
        tbZoneBusinessService.save(tbZoneBusiness);
        return new Result(byId);
    }

    //查询店铺
    //首页店铺与名字///
    @GetMapping("getsname")
    public Result getBid(BBusinessInfo bBusinessInfo) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if (bBusinessInfo.getId() != null) {
            queryWrapper.eq("id", bBusinessInfo.getId());
        }
        queryWrapper.select("sname", "id", "bicon");
        return new Result<>(bBusinessInfoService.list(queryWrapper));
    }

    //根据手机号查询该用户是否为商家
    @GetMapping("tokenphone")
    public Result getPhone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", phone);
        boolean one = bBusinessInfoService.getOne(queryWrapper) == null;
        return new Result(one);
    }

    //保存入驻商家审核信息
    @PostMapping("/storeinsert")
    public Result storeinsert(@RequestBody BBusinessInfo bBusinessInfo) {
        boolean save = bBusinessInfoService.saveOrUpdate(bBusinessInfo);
        return new Result(save);
    }
    //我的店铺回显
    @GetMapping("/showshop")
    public Result Show(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", phone);
        return new Result(bBusinessInfoService.getOne(queryWrapper));
    }
    //根据用户名查询用户信息
    @GetMapping("/getUserByName/{username}")
    public BBusinessInfo getBusiness(@PathVariable String username) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", username);
        queryWrapper.eq("status", 0);
        return bBusinessInfoService.getOne(queryWrapper);
    }
    @PostMapping("/updatePassword/{phone}/{newPassword}")
    public Result UpdateEmp(@PathVariable String phone,@PathVariable String newPassword){
        System.out.println("phone = " + phone);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone",phone);
        BBusinessInfo one = bBusinessInfoService.getOne(queryWrapper);
        one.setEnterdate(DateUtil.StringTime());
        one.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        boolean b = bBusinessInfoService.updateById(one);
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
