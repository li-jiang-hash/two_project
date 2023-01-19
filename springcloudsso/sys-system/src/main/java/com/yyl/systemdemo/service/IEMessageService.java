package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

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
