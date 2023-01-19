package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

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
