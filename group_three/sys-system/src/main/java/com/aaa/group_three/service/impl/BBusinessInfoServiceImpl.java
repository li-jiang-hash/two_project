package com.aaa.group_three.service.impl;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.group_three.dao.BAppealMapper;
import com.aaa.group_three.dao.BBusinessInfoMapper;
import com.aaa.group_three.service.IBBusinessInfoService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 商家信息表 服务实现类
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@Service
public class BBusinessInfoServiceImpl extends ServiceImpl<BBusinessInfoMapper, BBusinessInfo> implements IBBusinessInfoService {
    @Resource
    private BBusinessInfoMapper bBusinessInfoMapper;
    @Override
    public IPage getPageData(PageInfo page, BBusinessInfo bBusinessInfo) {
        IPage list=bBusinessInfoMapper.findAll(page,bBusinessInfo);
        return list;
    }
}
