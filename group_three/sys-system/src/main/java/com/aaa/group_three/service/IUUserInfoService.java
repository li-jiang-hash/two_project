package com.aaa.group_three.service;

import com.aaa.entity.UUserInfo;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-05
 */
public interface IUUserInfoService extends IService<UUserInfo> {

    Page getAll(PageInfo pageInfo, String startTime, String endTime);
}
