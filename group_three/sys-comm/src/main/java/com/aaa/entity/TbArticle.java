package com.aaa.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TbArticle implements Serializable {

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
     * 状态（1有效，0无效）
     */
    private Integer isDeleted;

    /**
     * 导航ID
     */
    private String navId;

    /**
     * 文章标题
     */
    private String artTitle;

    /**
     * 文章图片
     */
    private String artPic;

    /**
     * 文章描述
     */
    private String artDesc;


}
