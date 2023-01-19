package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbHeadNav;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
public interface ITbHeadNavService extends IService<TbHeadNav> {

    Page getPageData(PageInfo page, TbHeadNav headNav);
}
