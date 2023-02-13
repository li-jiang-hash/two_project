package com.aaa.entity;

import lombok.Data;

@Data
public class AlipayBean {

    /**
     * 商户订单号，必填
     *
     */
    private String code;
    /**
     * 订单名称，必填
     */
    private String gname;
    /**
     * 付款金额，必填
     * 根据支付宝接口协议，必须使用下划线
     */
    private String price;
    /**
     * 商品描述，可空
     */
    private String remark;
    /**
     * 超时时间参数
     */
    private String timeout_express= "10m";
    /**
     * 产品编号
     */
    private String product_code= "FAST_INSTANT_TRADE_PAY";

}
