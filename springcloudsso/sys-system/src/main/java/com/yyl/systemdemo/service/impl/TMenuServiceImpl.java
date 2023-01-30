package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyl.entity.TMenu;
import com.yyl.systemdemo.dao.TMenuMapper;
import com.yyl.systemdemo.service.ITMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@Service
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements ITMenuService {

    @Resource
    private TMenuMapper menuMapper;
    @Override
    public List<TMenu> getMenusByRids(List<Integer> rids) {
        return menuMapper.getMenuByRids(rids);
    }
}
