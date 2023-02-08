package com.aaa.group_three.service.impl;

import com.aaa.entity.TbBanner;
import com.aaa.group_three.dao.TbBannerMapper;
import com.aaa.group_three.service.ITbBannerService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-17
 */
@Service
public class TbBannerServiceImpl extends ServiceImpl<TbBannerMapper, TbBanner> implements ITbBannerService {

    @Override
    public Page getPageData(PageInfo page, TbBanner banner) {

        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(banner.getTitle())){
            queryWrapper.like("title",banner.getTitle());
        }

        return this.page(page,queryWrapper);
    }


}
