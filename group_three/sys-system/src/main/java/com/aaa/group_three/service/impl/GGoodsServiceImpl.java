package com.aaa.group_three.service.impl;

import com.aaa.entity.GGoods;
import com.aaa.group_three.dao.GGoodsMapper;
import com.aaa.group_three.service.IGGoodsService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-08
 */
@Service
public class GGoodsServiceImpl extends ServiceImpl<GGoodsMapper, GGoods> implements IGGoodsService {
    @Resource
    private GGoodsMapper goodsMapper;

    @Override
    public Page getAllGoods(PageInfo pageInfo, GGoods goods) {
        return goodsMapper.findAllGoods(pageInfo, goods);
    }

    @Override
    public Page getGoodsAll(PageInfo pageInfo, String id) {
        return goodsMapper.getGoodsAll(pageInfo, id);
    }

    @Override
    public Map<String, Object> getGoodsByGoodsid(Integer id) {
        return goodsMapper.getGoodsByGoodsid(id);
    }

    @Override
    public Page getAll(PageInfo pageInfo, String id, GGoods goods) {
        return goodsMapper.getAll(pageInfo,id,goods);
    }

}
