package com.aaa.group_three.service.impl;

import com.aaa.entity.BAppeal;
import com.aaa.entity.BBusinessInfo;
import com.aaa.group_three.dao.BAppealMapper;
import com.aaa.group_three.service.IBAppealService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家申诉表 服务实现类
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@Service
public class BAppealServiceImpl extends ServiceImpl<BAppealMapper, BAppeal> implements IBAppealService {
    @Resource
    private BAppealMapper bAppealMapper;
    @Override
    public IPage getPageData(PageInfo page, BAppeal bAppeal) {
        IPage list=bAppealMapper.findAll(page,bAppeal);
        return list;
    }
}
