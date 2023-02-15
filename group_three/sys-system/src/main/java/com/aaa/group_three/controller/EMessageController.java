package com.aaa.group_three.controller;


import com.aaa.entity.EMessage;
import com.aaa.group_three.service.IEMessageService;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 查询所有数据
     */
    @PostMapping
    public Result getAllMessage(PageInfo page, @RequestParam(defaultValue = "1") String startTime,String endTime){
        System.out.println("startTime = " + startTime);
        System.out.println("startTime = " + (startTime.equals("1")));
        Page page1 = messageService.getPageData(page,startTime,endTime);
        return new Result(page1);
    }

    /**
     * 添加公告信息
     * @param message
     * @return
     */
    @PostMapping("sendMessage")
    public Result addMessage(EMessage message){
        //创建时间
//        LocalDateTime dt = LocalDateTime.now ();
//        message.setGmtDate(dt);
        Date date = new Date();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(date);
        message.setGmtDate(format);
        return new Result(messageService.saveOrUpdate(message));
    }

    /**
     * 修改公告信息
     * @param message
     * @return
     */
    @PostMapping("updMessage")
    public Result updMessage(EMessage message){
        return new Result(messageService.saveOrUpdate(message));
    }


    /**
     * 删除公告信息
     */
    @DeleteMapping("delmessage/{id}")
    public Result delMessage(@PathVariable Integer id){
        System.out.println("jkdvhjk="+id);
//        messageService.removeById()
        return new Result(messageService.removeById(id));
    }

}

