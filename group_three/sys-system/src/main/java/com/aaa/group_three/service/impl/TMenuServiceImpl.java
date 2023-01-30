package com.aaa.group_three.service.impl;


import com.aaa.entity.TMenu;
import com.aaa.group_three.dao.TMenuMapper;
import com.aaa.group_three.service.ITMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
