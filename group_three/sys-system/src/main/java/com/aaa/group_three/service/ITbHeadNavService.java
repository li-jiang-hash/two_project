package com.aaa.group_three.service;

import com.aaa.entity.TbHeadNav;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Page getAll(Page page, TbHeadNav headNav);
}
