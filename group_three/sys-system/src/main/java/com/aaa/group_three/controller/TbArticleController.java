package com.aaa.group_three.controller;


import com.aaa.entity.TbArticle;
import com.aaa.group_three.service.ITbArticleService;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public Result getAllArticle(@PathVariable String navId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nav_id",navId);
        return new Result(articleService.getOne(queryWrapper));
    }

    /**
     * 修改文章内容
     * @param article
     * @return
     */
    @PostMapping("updateArticleByNavId")
    public Result addBottom(TbArticle article){
        Date date = new Date();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(date);
        article.setGmtCreate(format);
        article.setGmtModified(format);
        article.setIsDeleted(0);
        return new Result(articleService.updateById(article));
    }
}

