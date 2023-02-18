package com.aaa.order.dao;

import com.aaa.entity.OOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
public interface OOrderMapper extends BaseMapper<OOrder> {

    List<OOrder> findOrder(String userId, Integer zhuangtai);
}
