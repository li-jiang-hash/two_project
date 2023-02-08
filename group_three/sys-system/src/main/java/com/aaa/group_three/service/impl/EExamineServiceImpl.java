package com.aaa.group_three.service.impl;

import com.aaa.entity.EExamine;
import com.aaa.group_three.dao.EExamineMapper;
import com.aaa.group_three.service.IEExamineService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 审查表 服务实现类
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Service
public class EExamineServiceImpl extends ServiceImpl<EExamineMapper, EExamine> implements IEExamineService {
    @Resource
    private EExamineMapper eExamineMapper;

    @Override
    public Page getAll(PageInfo pageInfo, String startTime,String endTime) {
         Page list=eExamineMapper.findAll(pageInfo,startTime,endTime);
         return list;
    }
}
