package com.yyl.systemdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyl.entity.TRole;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
public interface TRoleMapper extends BaseMapper<TRole> {

    List<TRole> getRoleByUid(Integer uid);
}
