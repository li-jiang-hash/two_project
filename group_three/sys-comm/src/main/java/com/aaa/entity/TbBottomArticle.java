package com.aaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private String id;

    /**
     * 导航名称
     */
    private String name;

    /**
     * 启用状态
     */
    private Integer isDisable;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 父类id
     */
    private String parentId;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;


}
