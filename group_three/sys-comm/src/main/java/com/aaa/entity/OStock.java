package com.aaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品存货信息
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OStock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库存id
     */
    private String id;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品总数量
     */
    private Integer totalNum;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品售出数量
     */
    private Integer sellNum;

    /**
     * 商品剩余数量
     */
    private Integer residue;


}
