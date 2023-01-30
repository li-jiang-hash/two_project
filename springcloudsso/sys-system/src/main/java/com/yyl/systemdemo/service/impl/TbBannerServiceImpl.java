package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbBanner;
import com.yyl.systemdemo.dao.TbBannerMapper;
import com.yyl.systemdemo.service.ITbBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.util.PageInfo;
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
        System.out.println("banner的值："+banner);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(banner.getTitle())){
            queryWrapper.like("title",banner.getTitle());
        }

        return this.page(page,queryWrapper);
    }


}
