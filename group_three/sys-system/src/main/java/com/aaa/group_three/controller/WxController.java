package com.aaa.group_three.controller;

import com.aaa.entity.OOrder;
import com.aaa.entity.Order;
import com.aaa.group_three.service.PayService;
import com.aaa.group_three.service.impl.PayServiceImpl;
import com.aaa.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/*
 * @Author: 江Sir
 * @Date: 2023/02/17 18:32:20
 * @Description:
 * @Since version:  2022.10
 */

@RestController
@RequestMapping("/wx")
public class WxController {

    @Resource
    private PayService payService;

    @GetMapping("getPayStatus/{order}")
    @ApiOperation(value = "根据订单的编号获取订单的状态")
    public Result getPayStatus(@PathVariable("order") OOrder order) {
        Map<String, String> map = payService.getPayStatus(order);
        Result result = new Result();
        result.setCode(2500);
        result.setMsg("支付中");
        result.setData(map);
        return result;
    }


    @PostMapping("getNativeCodeUrl")
//    @ApiImplicitParams({@ApiImplicitParam(name="order")})
    public Result getNativeCodeUrl(@ModelAttribute Order order) {
        String num=Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
        System.out.println("orderorderorderorderorderorder11111111111111 = " + order);
        order.setOrderNum(num+"a");
        Map map = payService.getNativeByOrder(order);
        System.out.println("mapmapmapmapmapmapmapmapmapmapmapmap = " + map);
        return new Result(map);
    }
}
