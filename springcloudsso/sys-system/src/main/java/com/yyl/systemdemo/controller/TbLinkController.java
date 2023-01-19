package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbLink;
import com.yyl.systemdemo.service.ITbLinkService;
import com.yyl.util.PageInfo;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/tb-link")
public class TbLinkController {

    @Resource
    private ITbLinkService linkService;

    // 查询所有的role
    @PostMapping
    public Result getAllLink(PageInfo page, TbLink link){


        Page page1 = linkService.getPageData(page, link);
        return new Result(page1);
    }


    /**
     * 添加链接信息
     * @param link
     * @return
     */
    @PostMapping("addLink")
    public Result addLink(TbLink link){
        //saveOrUpdate  添加或修改
        //link  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(linkService.saveOrUpdate(link));
    }

    /**
     * 修改链接信息
     */
    @PostMapping("updisable/{linkId}/{isDisable}")
    public Result updLink(@PathVariable Integer linkId, @PathVariable Integer isDisable){
        TbLink link=new TbLink();
        link.setIsDisable(isDisable);
        link.setLinkId(linkId);
        return new Result(linkService.updateById(link));
    }


    /**
     * 删除链接信息
     */
    @DeleteMapping("{id}")
    public Result delLink(@PathVariable Integer id){
        return new Result(linkService.removeById(id));
    }


}

