package com.aaa.group_three.service;

import com.aaa.entity.GGoods;
import com.aaa.util.PageInfo;
import com.aaa.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-08
 */
public interface IGGoodsService extends IService<GGoods> {

    Page getAllGoods(PageInfo pageInfo, GGoods goods);
    Page getGoodsAll(PageInfo pageInfo, String id);
}
