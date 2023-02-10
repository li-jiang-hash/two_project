package com.aaa.group_three.controller;


import com.aaa.entity.TbZone;
import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.service.ITbZoneBusinessService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 专区课程关联表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/tb-zone-business")
public class TbZoneBusinessController {
    @Resource
    private ITbZoneBusinessService zoneBusinessService;

    /**
     * 首页店铺
     * @return
     */
    @GetMapping("zoneBusiness/selectZoneBusiness")
    public Result getZoneBusiness(){
        List<Map<String, Object>> business = zoneBusinessService.findBusiness();
        return new Result<>(business);
    }

    /**
     * 首页搜索
     * @param chandVal 搜索内容
     * @return
     */
    @GetMapping("findBusinessByName")
    public Result findBusinessByName(String chandVal, Integer pageCurrent, Integer pageSize){
        PageInfo pageInfo = new PageInfo(pageCurrent, pageSize);
        Page page = zoneBusinessService.findBusinessByName(pageInfo,chandVal);
        return new Result<>(page);
    }


    /**
     * 商户端-首页管理-专区管理—专区店铺-查询所有
     */
    @PostMapping("managementZone")
    public Result getAllZone(PageInfo page, TbZoneBusiness zoneBusiness){
        Page page1 = zoneBusinessService.getPageData(page, zoneBusiness);
        return new Result(page1);
    }
    /**
     * 商户端-首页管理-专区管理—专区店铺-修改状态
     */
    @PostMapping("changeStatus/{id}/{isDisable}")
    public Result updLink(@PathVariable String id, @PathVariable String isDisable){
        TbZoneBusiness zoneBusiness=new TbZoneBusiness();
        zoneBusiness.setIsDisable(isDisable);
        zoneBusiness.setId(id);
        return new Result(zoneBusinessService.updateById(zoneBusiness));
    }
    /**
     * 商户端-首页管理-专区管理—专区店铺-删除数据
     */
    @DeleteMapping("deleteZoneBusiness/{id}")
    public Result delLink(@PathVariable String id){
        return new Result(zoneBusinessService.removeById(id));
    }
}

