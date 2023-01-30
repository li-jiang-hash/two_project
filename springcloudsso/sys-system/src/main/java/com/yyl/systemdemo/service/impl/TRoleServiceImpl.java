package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyl.entity.TRole;
import com.yyl.systemdemo.dao.TRoleMapper;
import com.yyl.systemdemo.service.ITRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper, TRole> implements ITRoleService {
    @Resource
    private TRoleMapper tRoleMapper;

    @Override
    public List<TRole> getByUid(Integer uid) {
        return tRoleMapper.getRoleByUid(uid);
    }
}
