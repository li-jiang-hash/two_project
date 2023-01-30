package com.aaa.group_three.service;

import com.aaa.entity.RRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色权限连表 服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-08
 */
public interface IRRolePermissionService extends IService<RRolePermission> {

    boolean updPermission(String roleId, String menuids);
}
