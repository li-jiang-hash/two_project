package com.aaa.order.service.impl;

import com.aaa.entity.OOrder;
import com.aaa.order.dao.OOrderMapper;
import com.aaa.order.service.IOOrderService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Service
public class OOrderServiceImpl extends ServiceImpl<OOrderMapper, OOrder> implements IOOrderService {

    @Resource
    private OOrderMapper orderMapper;
    @Override
    public Page findOrder(PageInfo pageInfo, String userId, Integer zhuangtai) {
        return orderMapper.findOrder(pageInfo,userId,zhuangtai);
    }
}
