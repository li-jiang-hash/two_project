package com.aaa.project.service.Impl;

import com.aaa.entity.AlipayBean;
import com.aaa.entity.Order;
import com.aaa.project.service.PayService;
import com.aaa.project.util.Alipay;
import com.aaa.project.util.HttpClient;
import com.aaa.project.util.PayParamUtil;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;

    /**
     * 支付宝支付
     * @param alipayBean
     * @return
     * @throws
     */
    @Override
    public String aliPay(AlipayBean alipayBean) {
        System.out.println("alipayBean = " + alipayBean);
        return  null;
    }

    /**
     * 微信支付
     * @param
     * @return
     */
    /*@Override
    public Map getPayStatus(String orderNum,Integer orderid) {
        try {
            Map m = new HashMap();        //参数
                  m.put("appid", PayParamUtil.APPID);
                  m.put("mch_id", PayParamUtil.MCH_ID);//"1558950191"
                 m.put("out_trade_no", orderid+"");        //1.00    1
            // m.put("total_fee", new BigDecimal(order.getPrice()*order.getNum()).multiply(new BigDecimal("100")).longValue() + ""); //单位 分
             m.put("nonce_str", WXPayUtil.generateNonceStr());
             //m.put("sign", "127.0.0.1");
            // m.put("trade_type", "NATIVE");
              HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
              client.setXmlParam(WXPayUtil.generateSignedXml(m, PayParamUtil.API_KEY));
              client.setHttps(true);
              client.post();
              String xml = client.getContent();//xml
                 System.out.println("支付后的信息========" + xml);        //System.out.println("支付后的信息========" + xml);
                 Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
                 System.out.println("支付之后的信息的内容==================="+resultMap);
                 return resultMap;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
    @Override
    public Map getNativeByOrder(Order order) {
        try {
            //统一下单   生成预支付的订单
            java.util.Map m = new HashMap();
            //参数
            m.put(PayParamUtil.APP_ID, PayParamUtil.APPID);
            m.put("mch_id", PayParamUtil.MCH_ID);//"1558950191"
//            m.put("out_trade_no", order.getOrderNum());
//            //1.00  1
//            m.put("total_fee", new BigDecimal(order.getMoney()).multiply(new BigDecimal("100")).longValue() + ""); //单位 分
//            m.put("body", order.getDescribe());
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("trade_type", "NATIVE");
            m.put("notify_url", "http://www.chenkaixiang.top/notifyWeiXinPay\n");
            //统一下单
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayParamUtil.API_KEY));
            client.setHttps(true);
            client.post();
            //生成的预支付订单的消息
            String xml=client.getContent();//xml
            System.out.println("-------------"+xml);
            java.util.Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println(resultMap);
            Map map = new HashMap<>();
            //map.put("out_trade_no", orederNum);
            // map.put("course_id", 1);
            // map.put("total_fee", 1 + "");
            // map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            return map;
        }catch(Exception e){
            System.out.println(e.getMessage());

        }
        return null;
    }
    @Override
    public Map getPayStatus(String orderid) {
        try {
            Map m = new HashMap();
            //参数
            m.put("appid", PayParamUtil.APPID);
            m.put("mch_id", PayParamUtil.MCH_ID);//"1558950191"
            m.put("out_trade_no", orderid+"");
            //1.00    1
            // m.put("total_fee", new BigDecimal(order.getPrice()*order.getNum()).multiply(new BigDecimal("100")).longValue() + ""); //单位 分
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //m.put("sign", "127.0.0.1");
            // m.put("trade_type", "NATIVE");
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayParamUtil.API_KEY));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();//xml
            System.out.println("支付后的信息========" + xml);
            //System.out.println("支付后的信息========" + xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("支付之后的信息的内容==================="+resultMap);
            return resultMap;
        }catch(Exception e){
            e.printStackTrace();
        }    return null;
    }

}
