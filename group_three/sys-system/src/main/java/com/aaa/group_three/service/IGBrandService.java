package com.aaa.group_three.service;

import com.aaa.entity.GBrand;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
public interface IGBrandService extends IService<GBrand> {

    Page getAllBrands(PageInfo pageInfo, GBrand gBrand);
}
