package com.aaa.group_three.service.impl;

import com.aaa.entity.TbHeadNav;
import com.aaa.group_three.dao.TbHeadNavMapper;
import com.aaa.group_three.service.ITbHeadNavService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
@Service
public class TbHeadNavServiceImpl extends ServiceImpl<TbHeadNavMapper, TbHeadNav> implements ITbHeadNavService {

    @Resource
    private TbHeadNavMapper headNavMapper;

    @Override
    public Page getPageData(PageInfo page, TbHeadNav headNav) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(headNav.getHeadName()), "head_name", headNav.getHeadName());
        queryWrapper.like(StringUtils.isNotBlank(headNav.getIsDisable()), "is_disable", headNav.getIsDisable());
        return this.page(page, queryWrapper);
    }

    @Override
    public Page getAll(Page page, TbHeadNav headNav) {

        return headNavMapper.getAll(page, headNav);
    }
}