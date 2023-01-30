package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyl.entity.TUserRole;
import com.yyl.systemdemo.dao.TUserRoleMapper;
import com.yyl.systemdemo.service.ITUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@Service
public class TUserRoleServiceImpl extends ServiceImpl<TUserRoleMapper, TUserRole> implements ITUserRoleService {

}
