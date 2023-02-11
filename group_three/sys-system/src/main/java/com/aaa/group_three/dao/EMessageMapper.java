package com.aaa.group_three.dao;

import com.aaa.entity.EMessage;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 系统通知表 Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-12
 */
public interface EMessageMapper extends BaseMapper<EMessage> {

    Page getRole(PageInfo page, QueryWrapper queryWrapper);

//    Page findAll(PageInfo pageInfo, String startTime, String endTime);
}
