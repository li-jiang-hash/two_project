package com.aaa.group_three.controller;


import com.aaa.entity.TbBottomArticle;
import com.aaa.group_three.service.ITbBottomArticleService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanglei
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


    /**
     * 首页底部文章
     * @return
     */
    @GetMapping("/getQianAllBottomArticle")
    public Result getQianAllBottomArticle(){
        List<TbBottomArticle> articleList=new ArrayList<>();

        QueryWrapper<TbBottomArticle> wrapper = new QueryWrapper<>();
        wrapper.select("id","name");
        wrapper.eq("is_disable","0");
        wrapper.eq("parent_id","0");
        List<TbBottomArticle> parentList = bottomArticleService.list(wrapper);  //查询父节点

        wrapper.clear();
        wrapper.select("id","name","parent_id");
        wrapper.eq("is_disable","0");
        wrapper.ne("parent_id","0");
        List<TbBottomArticle> childList = bottomArticleService.list(wrapper); //查询字节点

        int i = 0;
        for (TbBottomArticle parent : parentList){
            for (TbBottomArticle child : childList){
                if (parent.getId().equals(child.getParentId())){
                    articleList.add(parent);
                    //将字节点放入 children
                    articleList.get(i).getChildren().add(child);
                    i++;
                }
            }
        }

        return new Result<>(articleList);
    }
}

