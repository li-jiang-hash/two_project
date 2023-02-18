package com.aaa.order.service;

import com.aaa.entity.OOrder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
public interface IOOrderService extends IService<OOrder> {

    IPage<OOrder> findOrder(Integer pageCurrent, Integer pageSize, String userId, Integer zhuangtai);
}
