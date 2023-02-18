package com.aaa.group_three.controller;



import com.aaa.entity.TbZone;
import com.aaa.group_three.service.ITbZoneService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 专区 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/tb-zone")
public class TbZoneController {
    @Resource
    private ITbZoneService zoneService;

    /**
     * 商品专区
     * @return
     */
    @GetMapping("zone/prefecture")
    public Result getZone(){
        QueryWrapper<TbZone> wrapper = new QueryWrapper<>();
        wrapper.eq("is_disable","0");
        wrapper.orderBy(true,true ,"sort");
        return new Result<>(zoneService.list(wrapper));
    }


    /**
     * 商户端查询专区所有信息
     * @param
     * @param zone
     * @return
     */
    @PostMapping("fuzzySearch")
    public Result getAll(PageInfo page,TbZone zone){
        Page page1 = zoneService.getPageData(page, zone);
        return new Result(page1);
    }



    /**
     * 修改专区商户端状态
     */
    @PostMapping("updateDisable/{id}/{isDisable}")
    public Result updZone(@PathVariable String id, @PathVariable String isDisable){
        TbZone zone=new TbZone();
        zone.setId(id);
        zone.setIsDisable(isDisable);
        System.out.println("1215jhbjb"+zone);
        return new Result(zoneService.updateById(zone));
    }

    /**
     * 商户端根据id删除数据
     */
    @DeleteMapping("removePrefecture/{id}")
    public Result delZone(@PathVariable String id){
        return new Result(zoneService.removeById(id));
    }


    /**
     * 商户端添加/修改数据
     * @param zone
     * @return
     */
    @PostMapping("addPrefecture")
    public Result addZone(TbZone zone){
        System.out.println("155nnnnnnnnnnnnnnnnnnn"+zone);
        return new Result( zoneService.save(zone));
    }

    @PostMapping("updatePrefecture")
    public Result updZone(TbZone zone){
        System.out.println("888nnnnnnnnnnnnnnnnnnn"+zone);
        return new Result( zoneService.updateById(zone));
    }

}

