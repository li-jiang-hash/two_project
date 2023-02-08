package com.aaa.group_three.service;

import com.aaa.entity.TbZone;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 专区 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
public interface ITbZoneService extends IService<TbZone> {

    Page getPageData(Page page, TbZone zone);

}
