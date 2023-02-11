package com.aaa.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author wanglei
 * @since 2023-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 收货地址
     */
    private String addrid;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;


}
