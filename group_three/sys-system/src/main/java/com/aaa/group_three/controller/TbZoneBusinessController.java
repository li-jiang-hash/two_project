package com.aaa.group_three.controller;


import com.aaa.group_three.service.ITbZoneBusinessService;
import com.aaa.util.Result;
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

}

