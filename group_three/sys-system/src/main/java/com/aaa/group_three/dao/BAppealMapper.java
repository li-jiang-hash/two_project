package com.aaa.group_three.dao;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.entity.EEmpInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家申诉表 Mapper 接口
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
public interface BAppealMapper extends BaseMapper<BAppeal> {
   IPage findAll(IPage page, @Param("bAppeal")BAppeal bAppeal);
}
