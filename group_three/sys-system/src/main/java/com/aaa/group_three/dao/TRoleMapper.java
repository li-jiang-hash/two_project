package com.aaa.group_three.dao;

import com.aaa.entity.TRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


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
