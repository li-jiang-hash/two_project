package com.aaa.group_three.service;

import com.aaa.entity.TbLink;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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
