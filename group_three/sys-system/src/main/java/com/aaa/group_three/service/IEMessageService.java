package com.aaa.group_three.service;

import com.aaa.entity.EMessage;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统通知表 服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-12
 */
public interface IEMessageService extends IService<EMessage> {

    Page getPageData(PageInfo page, EMessage message);
}
