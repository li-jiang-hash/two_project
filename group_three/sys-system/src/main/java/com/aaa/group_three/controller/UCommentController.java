package com.aaa.group_three.controller;


import com.aaa.entity.UComment;
import com.aaa.group_three.service.IUCommentService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
@RestController
@RequestMapping("/u-comment")
public class UCommentController {

    @Resource
    private IUCommentService commentService;

    /**
     * 查询用户评论
     * @return
     */
    @GetMapping("findComment")
    public Result findComment(String id, Integer pageCurrent, Integer pageSize, String userId){
        PageInfo pageInfo = new PageInfo(pageCurrent,pageSize);
        Page<UComment> page = commentService.findComment(id, userId, pageInfo);
        //将 img 转成 list
        for (int i = 0; i < page.getRecords().size(); i++) {
            String[] split = page.getRecords().get(i).getImg().split(",");
            page.getRecords().get(i).setImgs(Arrays.asList(split));
        }
        return new Result<>(page);
    }

    /**
     * 添加/修改评论
     * @return
     */
    @PostMapping("addComment")
    public Result addComment(@RequestBody UComment comment){
        //将 imgs 转成 String 并放入 img
        String img = String.join(",", comment.getImgs());
        comment.setImg(img);

        return new Result<>(commentService.saveOrUpdate(comment));
    }

    /**
     * 删除评论
     * @return
     */
    @GetMapping("deleteById")
    public Result deleteById(String id){
        UpdateWrapper<UComment> wrapper = new UpdateWrapper<>();
        wrapper.set("is_deleted",1);
        wrapper.eq("id",id);
        return new Result<>(commentService.update(wrapper));
    }

}

