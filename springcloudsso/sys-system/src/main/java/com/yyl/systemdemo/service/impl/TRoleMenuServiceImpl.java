package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyl.entity.TRoleMenu;
import com.yyl.systemdemo.dao.TRoleMenuMapper;
import com.yyl.systemdemo.service.ITRoleMenuService;
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
public class TRoleMenuServiceImpl extends ServiceImpl<TRoleMenuMapper, TRoleMenu> implements ITRoleMenuService {

}
