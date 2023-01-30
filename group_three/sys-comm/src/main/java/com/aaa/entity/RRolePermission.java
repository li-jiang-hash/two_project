package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限连表
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;

    private String isDeleted;

    private String gmtCreate;

    private String gmtModified;


}
