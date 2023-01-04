package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyl.entity.TUser;
import com.yyl.systemdemo.dao.TUserMapper;
import com.yyl.systemdemo.service.ITUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-19
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
