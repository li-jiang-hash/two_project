package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-18
 */
public interface ITbLinkService extends IService<TbLink> {

    Page getPageData(PageInfo page, TbLink link);
}
