package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.ERole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
public interface IERoleService extends IService<ERole> {

    Page getPageData(PageInfo page, ERole role);

    Map<String,Object> getPermissionData(Integer id);
}
