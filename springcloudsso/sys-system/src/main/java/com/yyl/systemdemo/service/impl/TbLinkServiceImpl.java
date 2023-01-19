package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbLink;
import com.yyl.systemdemo.dao.TbLinkMapper;
import com.yyl.systemdemo.service.ITbLinkService;
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
 * @since 2023-01-18
 */
@Service
public class TbLinkServiceImpl extends ServiceImpl<TbLinkMapper, TbLink> implements ITbLinkService {

    @Override
    public Page getPageData(PageInfo page, TbLink link) {

        System.out.println("link的值："+link);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(link.getLinkName())){
            queryWrapper.like("link_name",link.getLinkName());
        }

        return this.page(page,queryWrapper);
    }

}
