package com.aaa.group_three.dao;

import com.aaa.entity.GBrand;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 品牌 Mapper 接口
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
public interface GBrandMapper extends BaseMapper<GBrand> {

    Page getAll(PageInfo pageInfo, @Param("brands") GBrand gBrand);
}
