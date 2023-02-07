package com.aaa.group_three.controller;


import com.aaa.entity.BBusinessInfo;
import com.aaa.entity.TbZone;
import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.service.IBBusinessInfoService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IBBusinessInfoService ibBusinessInfoService;


}

