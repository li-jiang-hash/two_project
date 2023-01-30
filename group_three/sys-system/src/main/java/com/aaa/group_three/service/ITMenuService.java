package com.aaa.group_three.service;

import com.aaa.entity.TMenu;
import com.baomidou.mybatisplus.extension.service.IService;


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
