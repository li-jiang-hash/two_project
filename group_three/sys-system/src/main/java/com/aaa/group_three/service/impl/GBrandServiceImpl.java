package com.aaa.group_three.service.impl;

import com.aaa.entity.GBrand;
import com.aaa.group_three.dao.GBrandMapper;
import com.aaa.group_three.service.IGBrandService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Service
public class GBrandServiceImpl extends ServiceImpl<GBrandMapper, GBrand> implements IGBrandService {
@Resource
private GBrandMapper gBrandMapper;
    @Override
    public Page getAllBrands(PageInfo pageInfo, GBrand gBrand) {
        return gBrandMapper.getAll(pageInfo,gBrand);
    }
}
