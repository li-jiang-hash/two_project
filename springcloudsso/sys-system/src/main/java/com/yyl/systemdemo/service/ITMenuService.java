package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.entity.TMenu;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
public interface ITMenuService extends IService<TMenu> {

 List<TMenu> getMenusByRids(List<Integer> rids);
}
