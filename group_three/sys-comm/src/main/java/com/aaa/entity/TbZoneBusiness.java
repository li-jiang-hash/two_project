package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.ASSIGN_ID)
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
    private String isDisable;

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

    //b_business_info表字段
    private String Sname;

    @TableField(exist = false)
    private BBusinessInfo bBusinessInfo;
    @TableField(exist = false)
    private GSort gSort;
    @TableField(exist = false)
    private OStock oStock;
    @TableField(exist = false)
    private GGoods goods;
}
