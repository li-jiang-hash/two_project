package com.aaa.group_three.controller;


import com.aaa.entity.TbArticle;
import com.aaa.entity.TbBottomArticle;
import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.service.ITbArticleService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
@RestController
@RequestMapping("/tb-article")
public class TbArticleController {
    @Resource
    private ITbArticleService articleService;

    /**
     * 通过id查询对应文章
     * @param navId
     * @return
     */
    @PostMapping("getArticleByNavId/{navId}")
    public Result getAllArticle(String navId){
//        QueryWrapper queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("nav_id",navId);
//        List list = articleService.list(queryWrapper);
//        System.out.println(list);
        return new Result(articleService.getArticle(navId));
    }

    /**
     * 添加/修改
     * @param article
     * @return
     */
    @PostMapping("updateArticleByNavId")
    public Result addBottom(TbArticle article){

        return new Result(articleService.saveOrUpdate(article));
    }

}

