package com.aaa.group_three.controller;


import com.aaa.entity.TbBottomArticle;
import com.aaa.group_three.service.ITbBottomArticleService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/tb-bottom-article")
public class TbBottomArticleController {
    @Resource
    private ITbBottomArticleService bottomArticleService;

    // 查询所有的role
    @PostMapping
    public Result getAllBottom(PageInfo page, TbBottomArticle bottomArticle){


        Page page1 = bottomArticleService.getPageData(page, bottomArticle);
        return new Result(page1);
    }


    /**
     * 添加链接信息
     * @param bottomArticle
     * @return
     */
    @PostMapping("addBottom")
    public Result addBottom(TbBottomArticle bottomArticle){
        //saveOrUpdate  添加或修改
        //bottom  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(bottomArticleService.saveOrUpdate(bottomArticle));
    }

    /**
     * 修改链接信息
     */
    @PostMapping("updisable/{id}/{isDisable}")
    public Result updBottom(@PathVariable String id, @PathVariable Integer isDisable){
        TbBottomArticle bottomArticle=new TbBottomArticle();
        bottomArticle.setIsDisable(isDisable);
        bottomArticle.setId(id);
        return new Result(bottomArticleService.updateById(bottomArticle));
    }

    /**
     * 删除链接信息
     */
    @DeleteMapping("{id}")
    public Result delBottom(@PathVariable Integer id){
        return new Result(bottomArticleService.removeById(id));
    }


    @GetMapping("getQianAllBottomArticle")
    public Result getAll(){
        return new Result<>(bottomArticleService.list());
    }
}

