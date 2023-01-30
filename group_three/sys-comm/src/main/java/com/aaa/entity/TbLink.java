package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2023-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbLink implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Integer linkId;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 0启用 1禁用
     */
    private Integer isDisable;

    /**
     * 逻辑删除
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted=0;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}
