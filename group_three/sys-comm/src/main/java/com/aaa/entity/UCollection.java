package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 商家id
     */
    private String bid;

    /**
     * 商品id
     */
    private String goodsid;

    /**
     * 收藏类型 0商家 1商品
     */
    private Integer status;

    /**
     * 逻辑删除列
     */
    private Integer isDeleted;


}
