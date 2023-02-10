package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;

/**
 * <p>
 * 首页banner表
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted=0;

    /**
     * 创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime gmtModified;

    /**
     * 当前的启用状态
     */
    private String isDisable;


}
