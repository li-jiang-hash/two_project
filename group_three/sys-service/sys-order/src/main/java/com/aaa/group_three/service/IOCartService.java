package com.aaa.group_three.service;

import com.aaa.entity.OCart;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-11
 */
public interface IOCartService extends IService<OCart> {

    Page findCart(String userId, PageInfo pageInfo);
}
