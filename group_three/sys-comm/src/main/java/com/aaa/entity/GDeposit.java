package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 保存方式
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GDeposit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 储藏方式id
     */
    private Integer id;

    /**
     * 储藏方式名称
     */
    private String depositname;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 审核状态 0成功 1驳回 2待审核
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String checkContent;


}
