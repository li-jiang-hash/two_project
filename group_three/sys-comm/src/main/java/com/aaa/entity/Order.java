package com.aaa.entity;
//
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Order对象")
public class Order {
    /**
     * 订单好
     */
    @ApiModelProperty(value = "订单编号", required = true)
    private String orderNum;
    /**
     * 金额
     */
    @ApiModelProperty(value = "订单的金额", required = true)
    private String money;
    /**
     * |     * 描述
     */
    @ApiModelProperty(value = "订单的描述", required = true)
    private String describe;
}