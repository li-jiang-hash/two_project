package com.aaa.group_three.controller;


import com.aaa.entity.TbZone;
import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.service.ITbZoneBusinessService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.assertj.core.util.Lists.list;

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
        System.out.println(business);
        return new Result<>(business);
    }

}

