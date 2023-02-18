package com.aaa.order.service.impl;

import com.aaa.entity.OCart;
import com.aaa.order.dao.OCartMapper;
import com.aaa.order.service.IOCartService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-11
 */
@Service
public class OCartServiceImpl extends ServiceImpl<OCartMapper, OCart> implements IOCartService {

    @Resource
    private OCartMapper oCartMapper;
    @Override
    public Page findCart(String userId, PageInfo pageInfo) {
        return oCartMapper.findCart(userId, pageInfo);
    }
}
