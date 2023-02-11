package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbBottomArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 首页管理主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 导航名称
     */
    private String name;

    /**
     * 启用状态
     */
    private String isDisable;

    /**
     * 逻辑删除
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted=0;

    /**
     * 父类id
     */
    private String parentId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private List<TbBottomArticle> children = new ArrayList<>();


}
