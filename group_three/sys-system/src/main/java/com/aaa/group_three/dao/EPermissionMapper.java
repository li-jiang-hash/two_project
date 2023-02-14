package com.aaa.group_three.dao;

import com.aaa.entity.EPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 左侧菜单地址 Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
public interface EPermissionMapper extends BaseMapper<EPermission> {

    List<EPermission> getAllPermission();

    List<EPermission> getPermission(@Param("roleId") String rid);

    List<EPermission> getsonPermission(@Param("roleId")String rid, @Param("id")Integer id);
}
