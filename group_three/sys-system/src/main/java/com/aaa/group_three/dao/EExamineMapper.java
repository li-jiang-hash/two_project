package com.aaa.group_three.dao;

import com.aaa.entity.EExamine;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审查表 Mapper 接口
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
public interface EExamineMapper extends BaseMapper<EExamine> {

    Page findAll(PageInfo pageInfo,String startTime,String endTime);
}
