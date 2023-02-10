package com.aaa.group_three.service;

import com.aaa.entity.TbZoneBusiness;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专区课程关联表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
public interface ITbZoneBusinessService extends IService<TbZoneBusiness> {

    List<Map<String, Object>> findBusiness();

    Page getAll(Page page, TbZoneBusiness zoneBusiness);

    /**
     * 商户端-首页管理-专区管理—专区店铺-查询所有
     */
    Page getPageData(PageInfo page, TbZoneBusiness zoneBusiness);

    Page findBusinessByName(PageInfo pageInfo, String chandVal);
}
