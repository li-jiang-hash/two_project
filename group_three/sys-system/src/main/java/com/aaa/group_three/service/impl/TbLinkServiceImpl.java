package com.aaa.group_three.service.impl;

import com.aaa.entity.TbLink;
import com.aaa.group_three.dao.TbLinkMapper;
import com.aaa.group_three.service.ITbLinkService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        queryWrapper.like(StringUtils.isNotBlank(link.getIsDisable()), "is_disable", link.getIsDisable());
        return this.page(page,queryWrapper);
    }

}
