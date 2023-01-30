package com.aaa.group_three.service;

import com.aaa.entity.ERole;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
