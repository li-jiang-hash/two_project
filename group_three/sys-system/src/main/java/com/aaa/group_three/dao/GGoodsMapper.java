package com.aaa.group_three.dao;

import com.aaa.entity.GGoods;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-08
 */
public interface GGoodsMapper extends BaseMapper<GGoods> {

    Page getGoodsAll(PageInfo pageInfo, String id);

    Page findAllGoods(PageInfo pageInfo,@Param("goods") GGoods goods);
}
