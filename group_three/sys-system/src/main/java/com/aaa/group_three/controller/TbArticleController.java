package com.aaa.group_three.controller;


import com.aaa.entity.TbArticle;
import com.aaa.entity.TbBottomArticle;
import com.aaa.group_three.service.ITbArticleService;
import com.aaa.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    // 查询所有
    @PostMapping("getArticleByNavId/{id}")
    public Result getAllArticle(String id){

        return new Result(articleService.getById(id));
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

