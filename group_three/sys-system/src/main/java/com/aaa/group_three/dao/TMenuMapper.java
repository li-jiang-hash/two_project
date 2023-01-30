package com.aaa.group_three.dao;

import com.aaa.entity.TMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


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
