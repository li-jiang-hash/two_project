package com.yyl.systemdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyl.entity.TMenu;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
public interface TMenuMapper extends BaseMapper<TMenu> {

    List<TMenu> getMenuByRids(List<Integer> rids);
}
