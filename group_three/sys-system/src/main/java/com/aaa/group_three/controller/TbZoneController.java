package com.aaa.group_three.controller;


import com.aaa.entity.TbZone;
import com.aaa.group_three.service.ITbZoneService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        wrapper.select("id","zone_name","zone_desc");
        return new Result<>(zoneService.list(wrapper));
    }
}

