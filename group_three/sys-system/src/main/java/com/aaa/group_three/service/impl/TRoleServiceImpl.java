package com.aaa.group_three.service.impl;

import com.aaa.entity.TRole;
import com.aaa.group_three.dao.TRoleMapper;
import com.aaa.group_three.service.ITRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
