package com.aaa.group_three.service;

import com.aaa.entity.EPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 左侧菜单地址 服务类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
public interface IEPermissionService extends IService<EPermission> {

    List<EPermission> getAllMenus(String rid);
}
