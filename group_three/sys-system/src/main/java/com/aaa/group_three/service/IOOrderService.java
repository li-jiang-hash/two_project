package com.aaa.group_three.service;

import com.aaa.entity.OOrder;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page findOrder(PageInfo pageInfo, String userId);
}
