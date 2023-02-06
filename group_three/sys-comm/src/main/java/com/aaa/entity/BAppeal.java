package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商家申诉表
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BAppeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商家id
     */
    private String businessId;

    /**
     * 申诉时间
     */
    private String appealTime;

    /**
     * 申诉内容
     */
    private String info;

    /**
     * 商家申请解冻商铺申诉结果 0待审核 1通过 2驳回
     */
    private Integer status;


}
