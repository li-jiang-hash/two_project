package com.aaa.group_three.dao;

import com.aaa.entity.UUserInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-01-05
 */
public interface UUserInfoMapper extends BaseMapper<UUserInfo> {

    Page getAll(PageInfo pageInfo, String startTime, String endTime);
}
