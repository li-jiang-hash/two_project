package com.aaa.group_three.dao;

import com.aaa.entity.EExamine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 审查表 Mapper 接口
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
public interface EExamineMapper extends BaseMapper<EExamine> {
//    Page findAll(IPage page, @Param("eExamine") EExamine eExamine);
}
