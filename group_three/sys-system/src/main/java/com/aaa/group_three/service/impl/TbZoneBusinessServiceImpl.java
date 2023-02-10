package com.aaa.group_three.service.impl;

import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.dao.TbZoneBusinessMapper;
import com.aaa.group_three.service.ITbZoneBusinessService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专区课程关联表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-31
 */
@Service
public class TbZoneBusinessServiceImpl extends ServiceImpl<TbZoneBusinessMapper, TbZoneBusiness> implements ITbZoneBusinessService {

    @Resource
    private TbZoneBusinessMapper tbZoneBusinessMapper;
    @Override
    public List<Map<String, Object>> findBusiness() {

        return tbZoneBusinessMapper.findBusiness();
        }

    @Override
    public Page getAll(Page page, TbZoneBusiness zoneBusiness) {

        return tbZoneBusinessMapper.getAll(page,zoneBusiness);
    }

    @Override
    public Page findBusinessByName(PageInfo pageInfo, String chandVal) {
        return tbZoneBusinessMapper.findBusinessByName(pageInfo,chandVal);
    }


    /**
     * 商户端-首页管理-专区管理—专区店铺-查询所有
     */
    @Override
    public Page getPageData(PageInfo page, TbZoneBusiness zoneBusiness) {
        System.out.println("zone的值："+zoneBusiness);
        System.out.println("page的值："+page);
        QueryWrapper queryWrapper = new QueryWrapper();
        if(zoneBusiness.getIsDisable()!=null){
            queryWrapper.eq("zb.is_disable",zoneBusiness.getIsDisable());
        }
        if(zoneBusiness.getZoneId()!=null){
            queryWrapper.eq("zb.zone_id",zoneBusiness.getZoneId());
        }
        if(zoneBusiness.getPutaway()!=null){
            queryWrapper.eq("zb.putaway",zoneBusiness.getPutaway());
        }
        // "" null
        if(StringUtils.isNotBlank(zoneBusiness.getSname())){
            queryWrapper.like("bb.sname",zoneBusiness.getSname());
        }
        return tbZoneBusinessMapper.getAllZone(page,queryWrapper);
    }
}
