package com.aaa.group_three.service.impl;

import com.aaa.entity.GSort;
import com.aaa.group_three.dao.GSortMapper;
import com.aaa.group_three.service.IGSortService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 商品类别 服务实现类
 * </p>
 *
 * @author wpp
 * @since 2023-02-01
 */
@Service
public class GSortServiceImpl extends ServiceImpl<GSortMapper, GSort> implements IGSortService {

}
