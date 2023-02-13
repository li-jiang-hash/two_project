package com.aaa.group_three.dao;

import com.aaa.entity.UCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
public interface UCollectionMapper extends BaseMapper<UCollection> {

    List<Map<String,Object>> findAll(String uid);

    List<Map<String, Object>> findGoods(String uid);
}
