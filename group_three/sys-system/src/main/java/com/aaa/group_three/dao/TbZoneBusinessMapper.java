package com.aaa.group_three.dao;

import com.aaa.entity.TbZoneBusiness;
import com.aaa.util.PageInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专区课程关联表 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
public interface TbZoneBusinessMapper extends BaseMapper<TbZoneBusiness> {

    List<Map<String, Object>> findBusiness();

    Page getAll(Page page, @Param("zoneBusiness") TbZoneBusiness zoneBusiness);


    /**
     * 商户端-首页管理-专区管理—专区店铺-查询所有
     */
    Page getAllZone(PageInfo page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Page findBusinessByName(PageInfo pageInfo, String chandVal);
}
