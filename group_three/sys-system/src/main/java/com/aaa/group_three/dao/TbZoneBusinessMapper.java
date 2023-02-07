package com.aaa.group_three.dao;

import com.aaa.entity.TbZoneBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
