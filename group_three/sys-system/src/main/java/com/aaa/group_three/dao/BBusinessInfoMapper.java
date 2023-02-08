package com.aaa.group_three.dao;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 商家信息表 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
public interface BBusinessInfoMapper extends BaseMapper<BBusinessInfo> {

    Page findAll(IPage page, @Param("bBusinessInfo") BBusinessInfo bBusinessInfo);
}
