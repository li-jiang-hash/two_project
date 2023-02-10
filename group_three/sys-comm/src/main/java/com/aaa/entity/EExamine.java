package com.aaa.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 审查表
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EExamine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审查id
     */
    private String id;

    /**
     * 店铺id
     */
    private String bid;

    /**
     * 审核人(管理员)
     */
    private String checkman;

    /**
     * 审查时间
     */
//    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
//   @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 停业原因
     */
    private String msg;

    /**
     * 0未删除 1 删除	
     */
    private Integer isDeleted;

    /**
     * 0通过 1审查不合格
     */
    private String state;

}
