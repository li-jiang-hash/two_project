package com.aaa.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品类别
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    private Integer id;

    /**
     * 类别名称
     */
    private String sortname;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 0通过审核 1驳回 2待审核
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String checkContent;



}
