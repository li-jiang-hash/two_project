package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbHeadNav implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "head_id", type = IdType.AUTO)
    private Integer headId;

    private String headName;

    private String href;

    /**
     * 启用状态 0启用  1未启用
     */
    private String isDisable;

    /**
     * 逻辑删除 0存在 1已删除
     */
    private String isDeleted;

    /**
     * 跳转方式  0新窗口 1同窗口
     */
    private Integer skipWay;

    /**
     * 导航标题顺序
     */
    private Integer sort;

    /**
     * 是否被添加  0被添加  1 没有被添加
     */
    private Integer isAdd;


}
