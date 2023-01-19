package com.yyl.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统通知表
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 发送日期
     */
    private LocalDateTime gmtDate;

    /**
     * 管理员id
     */
    private String eid;

    /**
     * 发送类型
     */
    private String stype;

    /**
     * 发送内容
     */
    private String scontend;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted=0;


}
