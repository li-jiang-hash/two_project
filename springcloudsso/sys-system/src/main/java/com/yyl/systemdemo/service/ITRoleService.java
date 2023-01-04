package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.entity.TRole;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
public interface ITRoleService extends IService<TRole> {
    /**
     * 根据用户的id查询用户的角色信息
     *
     */
    List<TRole> getByUid(Integer uid);

}
