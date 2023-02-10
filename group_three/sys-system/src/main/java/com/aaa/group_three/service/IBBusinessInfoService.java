package com.aaa.group_three.service;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商家信息表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
public interface IBBusinessInfoService extends IService<BBusinessInfo> {
    Page getPageData(PageInfo page, BBusinessInfo bBusinessInfo);

}
