package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TbArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

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
