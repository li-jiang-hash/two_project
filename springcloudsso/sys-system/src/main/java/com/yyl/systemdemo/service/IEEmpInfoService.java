package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EEmpInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

import java.util.List;

/**
 * <p>
 * 管理员信息表 服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
public interface IEEmpInfoService extends IService<EEmpInfo> {

    Page getPageData(PageInfo page, EEmpInfo empInfo);

    /**
     * 根据员工的rid查询员工的角色信息
     *
     */
    List<EEmpInfo> getById(Integer id);

}
