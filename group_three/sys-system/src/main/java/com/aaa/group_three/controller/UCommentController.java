package com.aaa.group_three.controller;


import com.aaa.entity.UComment;
import com.aaa.group_three.service.IUCommentService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 用户评论
     * @return
     */
    @GetMapping("findComment/{id}/{pageCurrent}/{pageSize}")
    public Result findComment(@PathVariable String id, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize){
        PageInfo pageInfo = new PageInfo(pageCurrent,pageSize);
        Page<UComment> page = commentService.findComment(id, pageInfo);
        //将 img 转成 list
        for (int i = 0; i < page.getRecords().size(); i++) {
            String[] split = page.getRecords().get(i).getImg().split(",");
            page.getRecords().get(i).setImgs(Arrays.asList(split));
        }

        return new Result<>(page);
    }

}

