package com.aaa.group_three.service.impl;

import com.aaa.entity.TbBanner;
import com.aaa.entity.TbZone;
import com.aaa.group_three.dao.TbZoneMapper;
import com.aaa.group_three.service.ITbZoneService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 专区 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@Service
public class TbZoneServiceImpl extends ServiceImpl<TbZoneMapper, TbZone> implements ITbZoneService {
    @Resource
    private TbZoneMapper zoneMapper;

    @Override
    public Page getPageData(Page page, TbZone zone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(zone.getZoneName())){
            queryWrapper.like("zone_name",zone.getZoneName());
        }
        queryWrapper.like(StringUtils.isNotBlank(zone.getIsDisable()), "is_disable", zone.getIsDisable());
        return this.page(page,queryWrapper);
    }

}
