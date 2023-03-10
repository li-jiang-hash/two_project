package com.aaa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色员工连表
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class REmpRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 员工id
     */
    private String empId;


}
