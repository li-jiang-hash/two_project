package com.aaa.project.service;

import com.aaa.entity.AlipayBean;
import com.aaa.entity.Order;

import java.util.Map;


public interface PayService {
    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) ;

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
    Map getNativeByOrder(Order order);

    Map getPayStatus(String orderid);

    //Map<String,String> getPayStatus(String orderNum,Integer orderid);

}
