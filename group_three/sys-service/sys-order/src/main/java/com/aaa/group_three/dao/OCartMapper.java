package com.aaa.group_three.dao;

import com.aaa.entity.OCart;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-11
 */
public interface OCartMapper extends BaseMapper<OCart> {

    Page findCart(String userId, PageInfo pageInfo);
}
