package com.aaa.group_three.service.impl;

import com.aaa.entity.OStock;
import com.aaa.group_three.dao.OStockMapper;
import com.aaa.group_three.service.IOStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品存货信息 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Service
public class OStockServiceImpl extends ServiceImpl<OStockMapper, OStock> implements IOStockService {

}
