package com.aaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 专区
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbZone implements Serializable {

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
     * 逻辑删除(0:正常;1:禁用)
     */
    private Integer isDeleted;

    /**
     * 是否启用
     */
    private Integer isDisable;

    /**
     * 名称
     */
    private String zoneName;

    /**
     * 描述
     */
    private String zoneDesc;

    /**
     * 排序
     */
    private Integer sort;


}
