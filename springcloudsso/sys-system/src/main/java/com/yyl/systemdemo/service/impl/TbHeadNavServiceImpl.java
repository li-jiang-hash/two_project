package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EEmpInfo;
import com.yyl.entity.TbHeadNav;
import com.yyl.systemdemo.dao.TbHeadNavMapper;
import com.yyl.systemdemo.service.ITbHeadNavService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
@Service
public class TbHeadNavServiceImpl extends ServiceImpl<TbHeadNavMapper, TbHeadNav> implements ITbHeadNavService {

    @Override
    public Page getPageData(PageInfo page, TbHeadNav headNav) {

        System.out.println("headNav的值："+headNav);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(headNav.getHeadName())){
            queryWrapper.like("head_name",headNav.getHeadName());
        }

        return this.page(page,queryWrapper);
    }

}
