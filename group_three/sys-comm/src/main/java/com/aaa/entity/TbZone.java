package com.aaa.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 专区
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_zone")
public class TbZone implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建时间
     */

//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
   //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String gmtCreate;

    /**
     * 修改时间
     */
//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
   //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String gmtModified;

    /**
     * 逻辑删除(0:正常;1:禁用)
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted=0;

    /**
     * 是否启用
     */
    private String isDisable;

    /**
     * 名称
     */
    private String zoneName;

    /**
     * 描述
     */
    private String zoneDesc;

    /**
     * 排序
     */
    private Integer sort;


}
