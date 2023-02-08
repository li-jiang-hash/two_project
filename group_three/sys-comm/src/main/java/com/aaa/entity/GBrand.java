package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    private Integer id;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 0审核成功 1驳回 2待审核
     */
    private Integer status;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String checkContent;

    /**
     * 品牌所属类别
     */
    private Integer sortid;


}
