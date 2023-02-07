package com.aaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 专区课程关联表
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbZoneBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 状态(1:正常;0:禁用)
     */
    private Integer isDisable;

    /**
     * 逻辑删除  0正常  1已删除
     */
    private Integer isDeleted;

    /**
     * 专区编号
     */
    private String zoneId;

    /**
     * 商家id
     */
    private String bid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 上下架   0上架  1 未上架
     */
    private Integer putaway;

}
