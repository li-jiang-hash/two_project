package com.aaa.group_three.service;

import com.aaa.entity.TbBanner;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-17
 */
public interface ITbBannerService extends IService<TbBanner> {

    Page getPageData(PageInfo page, TbBanner banner);
}
