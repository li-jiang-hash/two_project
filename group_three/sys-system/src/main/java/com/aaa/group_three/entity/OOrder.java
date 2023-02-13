package com.aaa.group_three.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author wanglei
 * @since 2023-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id（哪个用户买的）
     */
    private String uid;

    /**
     * 订单时间
     */
    private LocalDateTime orderdate;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 0 交易成功  1 待支付 2逻辑删除  3失效订单 4取消订单
     */
    private Integer state;

    /**
     * 商家id
     */
    private String bid;

    /**
     * 是否 发货  0 未发货  1 已发货
     */
    private Integer send;

    /**
     * 是否评论 0未评论 1评论
     */
    private Integer commentstatus;

    /**
     * 商品id
     */
    private String goodsid;

    /**
     * 下单数量
     */
    private Integer num;

    /**
     * 总价
     */
    private BigDecimal totalmoney;

    /**
     * 发货地址id
     */
    private String addrid;

    /**
     * 0微信 1支付宝
     */
    private Integer paysort;


}
