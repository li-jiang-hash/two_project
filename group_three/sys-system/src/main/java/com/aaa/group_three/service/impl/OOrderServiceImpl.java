package com.aaa.group_three.service.impl;

import com.aaa.entity.OOrder;
import com.aaa.group_three.dao.OOrderMapper;
import com.aaa.group_three.service.IOOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Service
public class OOrderServiceImpl extends ServiceImpl<OOrderMapper, OOrder> implements IOOrderService {

}
