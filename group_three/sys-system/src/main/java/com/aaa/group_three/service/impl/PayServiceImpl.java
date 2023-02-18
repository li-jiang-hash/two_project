package com.aaa.group_three.service.impl;

import com.aaa.entity.OOrder;
import com.aaa.entity.Order;
import com.aaa.group_three.config.HttpClient;
import com.aaa.group_three.config.PayConfigUtil;
import com.aaa.group_three.service.PayService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
 * @Author: 江Sir
 * @Date: 2023/02/17 19:03:21
 * @Description:
 * @Since version:  2022.10
 */
@Service
public class PayServiceImpl implements PayService {



    @Override
    public Map getNativeByOrder(Order order) {
        try {
            //统一下单   生成预支付的订单
            Map m = new HashMap();
            //参数
            m.put(PayConfigUtil.APP_ID, PayConfigUtil.APPID);
            m.put("mch_id", PayConfigUtil.MCH_ID);
            //"1558950191"
            m.put("out_trade_no", order.getDescribe());
            //1.00    1
            m.put("total_fee", new BigDecimal(order.getMoney()).multiply(new BigDecimal("100")).longValue() + "");
            //单位 分
            m.put("body", order.getDescribe());
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("trade_type", "NATIVE");
            m.put("notify_url", "http://www.chenkaixiang.top/notifyWeiXinPay\n");
            //统一下单
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayConfigUtil.API_KEY));
            client.setHttps(true);        client.post();
            //生成的预支付订单的消息
             String xml=client.getContent();
             //xml
            System.out.println("-------------"+xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println(resultMap);
            Map map = new HashMap<>();
            map.put("price", order.getMoney());
//            map.put("course_id", 1);
//            map.put("total_fee", 1 + "");
//            map.put("result_code", resultMap.get("result_code"));
            map.put("orderNo",order.getOrderNum());
            map.put("code_url", resultMap.get("code_url"));
            return map;
        }catch(Exception e){
             System.out.println(e.getMessage());
//            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Map getPayStatus(OOrder order) {
        try {
            Map m = new HashMap();
//            参数
           m.put("appid", PayConfigUtil.APPID);
           m.put("mch_id", PayConfigUtil.MCH_ID);
           //"1558950191"
            m.put("out_trade_no", order.getId()+"");
            //1.00    1
            m.put("total_fee", new BigDecimal(order.getPrice()*order.getNum()).multiply(new BigDecimal("100")).longValue() + "");
        //单位 分
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //m.put("sign", "127.0.0.1");
            m.put("trade_type", "NATIVE");
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayConfigUtil.API_KEY));        client.setHttps(true);
            client.post();
            String xml = client.getContent();
            //xml
            System.out.println("支付后的信息========" + xml);
            //System.out.println("支付后的信息========" + xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("支付之后的信息的内容==================="+resultMap);
            return resultMap;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
