package com.aaa.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ERole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 状态 0启用 1未启用
     */
    private String status;

    /**
     * 逻辑删除 0 未删除 1 删除
     */
    @TableLogic // 逻辑删除
    private Integer isDeleted;


}
