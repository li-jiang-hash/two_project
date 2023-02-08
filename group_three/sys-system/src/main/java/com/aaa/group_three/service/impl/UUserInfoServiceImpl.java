package com.aaa.group_three.service.impl;

import com.aaa.entity.UUserInfo;
import com.aaa.group_three.dao.UUserInfoMapper;
import com.aaa.group_three.service.IUUserInfoService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-01-05
 */
@Service
public class UUserInfoServiceImpl extends ServiceImpl<UUserInfoMapper, UUserInfo> implements IUUserInfoService {
    @Resource
    private UUserInfoMapper uUserInfoMapper;

    @Override
    public Page getAll(PageInfo pageInfo, String startTime, String endTime) {
        return uUserInfoMapper.getAll(pageInfo,startTime,endTime);
    }
}
