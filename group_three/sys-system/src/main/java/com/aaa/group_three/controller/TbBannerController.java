package com.aaa.group_three.controller;


import com.aaa.entity.TbBanner;
import com.aaa.group_three.service.ITbBannerService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-01-17
 */
@RestController
@RequestMapping("/tb-banner")
public class TbBannerController {

    @Resource
    private ITbBannerService bannerService;

    // 查询所有的banner
    @PostMapping
    public Result getAllBanner(PageInfo page, TbBanner banner){


        Page page1 = bannerService.getPageData(page, banner);
        return new Result(page1);
    }

    /**
     * 修改轮播banner状态
     */
    @PostMapping("updisable/{id}/{isDisable}")
    public Result updBanner(@PathVariable Integer id, @PathVariable String isDisable){
        TbBanner tbBanner=new TbBanner();
        tbBanner.setIsDisable(isDisable);
        tbBanner.setId(id);
        return new Result(bannerService.updateById(tbBanner));
    }


    /**
     * 删除banner信息
     */
    @DeleteMapping("{id}")
    public Result delBanner(@PathVariable Integer id){
        return new Result(bannerService.removeById(id));
    }


    /**
     * 添加banner信息
     * @param banner
     * @return
     */
    @PostMapping("addBanner")
    public Result addBanner(TbBanner banner){
        //saveOrUpdate  添加或修改
        //banner  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(bannerService.saveOrUpdate(banner));
    }

    /**
     * 首页轮播图
     * @return
     */
    @GetMapping("/rotationalSeeding")
    public Result getRotationalSeeding(){
        QueryWrapper<TbBanner> wrapper = new QueryWrapper<>();
        wrapper.eq("is_disable","0");
        wrapper.select("id","title","image_url","link_url","sort");
        return new Result<>(bannerService.list(wrapper));
    }
}

