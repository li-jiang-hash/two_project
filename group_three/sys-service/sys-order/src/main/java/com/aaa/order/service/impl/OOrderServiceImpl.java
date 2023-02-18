package com.aaa.order.service.impl;

import com.aaa.entity.OOrder;
import com.aaa.order.dao.OOrderMapper;
import com.aaa.order.service.IOOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public IPage<OOrder> findOrder(Integer pageCurrent, Integer pageSize, String userId, Integer zhuangtai) {
        IPage<OOrder> iPage = new Page<>(pageCurrent,pageSize);
        List<OOrder> orderList = orderMapper.findOrder(userId, zhuangtai);
        iPage.setRecords(orderList);
        iPage.setTotal(orderList.size());
        return iPage;
    }
}
