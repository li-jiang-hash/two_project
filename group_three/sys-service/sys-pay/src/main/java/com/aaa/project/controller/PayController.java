package com.aaa.project.controller;

import com.aaa.entity.AlipayBean;
import com.aaa.entity.GGoods;
import com.aaa.entity.Order;
//import com.aaa.project.service.IGGoodsService;
import com.aaa.project.service.PayService;
import com.aaa.util.Result;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@SuppressWarnings("all")
public class PayController {
//    @Autowired
//    private PayService payService;
////    @Autowired
////    private IGGoodsService igGoodsService;
//
//    @PostMapping(value = "order/alipay1")
//    public Result aaa(@ModelAttribute AlipayBean alipayBean) throws Exception {
//        System.out.println(alipayBean);
////        GGoods gGoods = igGoodsService.find(alipayBean.getCode());
//        System.out.println("gGoods = " + gGoods);
//        String code = alipayBean.getCode();
//        String price = alipayBean.getPrice();
//        String gname = gGoods.getGname();
//        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace().preCreate(gname, code, price);
//        Map map = new HashMap<>();
//        if (ResponseChecker.success(response)) {
//            String qrCode = response.getQrCode();
//            map.put("codeUrl", qrCode);  //生成的二维码链接，不可直接打开，后面会说到
//            map.put("price",alipayBean.getPrice());
////            map.put("orderId", orderId); //生成的订单号应该要返回给前端
////            integer = orderService.insertOrder(order); //插入订单数据库
//        } else {
//            //integer = 0;
//            map.put("codeUrl", "");
//            map.put("price", "");
//        }
//
//        return new Result(map);
//    }
////    @ApiOperation(value="生成二维码")
//    @PostMapping("getNativeCodeUrl")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name="order")
////    })
//    public Result getNativeCodeUrl(String orderName,String money,String describe){
//        Order order = new Order();
//        order.setOrderNum(orderName);
//        order.setMoney(money);
//        System.out.println("orderNmae="+orderName);
//        Map map=payService.getNativeByOrder(order);
//        return new Result(map);
//    }
//    @GetMapping("payForOrder/{code}")
////    @ApiOperation(value="根据订单的编号获取订单的状态")
//    public Result getPayStatus(@PathVariable("code") String orderNum) {
//        Map<String, String> map = payService.getPayStatus(orderNum);
//        Result result = new Result();
//        result.setCode(200);
//        result.setMsg("支付中");
//        result.setData(map);
//        return result;
//    }


}
