package com.aaa.group_three.service;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家申诉表 服务类
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
public interface IBAppealService extends IService<BAppeal> {
    IPage getPageData(PageInfo page, BAppeal bAppeal);

}
