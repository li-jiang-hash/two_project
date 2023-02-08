package com.aaa.group_three.service;

import com.aaa.entity.TbZoneBusiness;
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
}
