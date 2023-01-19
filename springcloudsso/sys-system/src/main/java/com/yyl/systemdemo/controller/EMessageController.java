package com.yyl.systemdemo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EMessage;
import com.yyl.systemdemo.service.IEMessageService;
import com.yyl.util.PageInfo;
import com.yyl.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统通知表 前端控制器
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-12
 */
@RestController
@RequestMapping("/e-message")
public class EMessageController {
    @Resource
    private IEMessageService messageService;

    // 查询所有的emp
    @PostMapping
    public Result getAllMessage(PageInfo page, EMessage message){


        Page page1 = messageService.getPageData(page, message);
        return new Result(page1);
    }

    /**
     * 添加公告信息
     * @param message
     * @return
     */
    @PostMapping("sendMessage")
    public Result addMessage(EMessage message){
        //创建/修改时间
        LocalDateTime dt = LocalDateTime.now ();
        message.setGmtDate(dt);
        //saveOrUpdate  添加或修改
        //message  对象有id的值的时候 修改
        //id没有值的时候 添加
        return new Result(messageService.saveOrUpdate(message));
    }



    /**
     * 删除公告、信息
     */
    @DeleteMapping("{id}")
    public Result delMessage(@PathVariable Integer id){
        return new Result(messageService.removeById(id));
    }

}

