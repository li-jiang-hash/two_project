package com.yyl.systemdemo.service.impl;

import com.aaa.entity.UUserInfo;
import com.aaa.group_three.dao.UUserInfoMapper;
import com.aaa.group_three.service.IUUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
