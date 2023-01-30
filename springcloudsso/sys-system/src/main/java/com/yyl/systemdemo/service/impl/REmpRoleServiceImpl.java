package com.yyl.systemdemo.service.impl;

import com.yyl.entity.REmpRole;
import com.yyl.systemdemo.dao.REmpRoleMapper;
import com.yyl.systemdemo.service.IREmpRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色员工连表 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
@Service
public class REmpRoleServiceImpl extends ServiceImpl<REmpRoleMapper, REmpRole> implements IREmpRoleService {

}
