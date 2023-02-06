package com.aaa.group_three.dao;

import com.aaa.entity.BAppeal;
import com.aaa.entity.EExamine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    IPage findAll(IPage page, @Param("eExamine") EExamine eExamine);
}
