package com.aaa.project.util;

import com.aaa.project.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Alipay {
    @Autowired
    private AlipayConfig alipayConfig;


    /**
     * 支付接口
     * @param alipayBean
     * @return
     * @throws
     */
//    public String pay(AlipayBean alipayBean)  {
//        // 1、获得初始化的AlipayClient
//        String serverUrl = alipayConfig.getGatewayUrl();
//        System.out.println("serverUrl = " + serverUrl);
//        String appId = alipayConfig.getAppId();
//        System.out.println("appId = " + appId);
//        String privateKey = alipayConfig.getPrivateKey();
//        System.out.println("privateKey = " + privateKey);
//        String format = "json";
//        String charset = alipayConfig.getCharset();
//        System.out.println("charset = " + charset);
//        String alipayPublicKey = alipayConfig.getPublicKey();
//        System.out.println("alipayPublicKey = " + alipayPublicKey);
//        String signType = alipayConfig.getSignType();
//        System.out.println("signType = " + signType);
//        String returnUrl = alipayConfig.getReturnUrl();
//        System.out.println("returnUrl = " + returnUrl);
//        String notifyUrl = alipayConfig.getNotifyUrl();
//        System.out.println("notifyUrl = " + notifyUrl);
//        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
//        // 2、设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        // 页面跳转同步通知页面路径
//        alipayRequest.setReturnUrl(returnUrl);
//        // 服务器异步通知页面路径
//        alipayRequest.setNotifyUrl(notifyUrl);
//        // 封装参数
//        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
//        // 3、请求支付宝进行付款，并获取支付结果
//        String result = alipayClient.pageExecute(alipayRequest).getBody();
//        // 返回付款信息
//        return result;
//    }
}
