package com.aaa.group_three.service.impl;

import com.aaa.entity.TbZoneBusiness;
import com.aaa.group_three.dao.TbZoneBusinessMapper;
import com.aaa.group_three.service.ITbZoneBusinessService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
