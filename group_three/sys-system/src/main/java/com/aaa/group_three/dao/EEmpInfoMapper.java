package com.aaa.group_three.dao;

import com.aaa.entity.EEmpInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员信息表 Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
public interface EEmpInfoMapper extends BaseMapper<EEmpInfo> {
//    List<EEmpInfo> getRoleById(Integer id);

    Page getRole(PageInfo page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
