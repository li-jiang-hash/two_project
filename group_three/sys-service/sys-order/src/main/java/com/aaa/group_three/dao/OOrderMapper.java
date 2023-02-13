package com.aaa.group_three.dao;

import com.aaa.entity.OOrder;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
public interface OOrderMapper extends BaseMapper<OOrder> {

    Page findOrder(PageInfo pageInfo, String userId);
}
