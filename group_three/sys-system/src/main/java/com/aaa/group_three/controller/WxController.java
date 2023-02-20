package com.aaa.group_three.controller;

import com.aaa.entity.OOrder;
import com.aaa.entity.Order;
import com.aaa.group_three.service.PayService;
import com.aaa.util.Result;
import io.swagger.annotations.ApiOperation;
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
        String num=Integer.toString((int) ((Math.random() * 9 + 1) * 100000));;
        order.setOrderNum(num+"");
        Map map = payService.getNativeByOrder(order);
        return new Result(map);
    }
}
