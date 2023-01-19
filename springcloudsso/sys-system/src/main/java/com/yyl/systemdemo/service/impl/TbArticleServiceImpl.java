package com.yyl.systemdemo.service.impl;

import com.yyl.entity.TbArticle;
import com.yyl.systemdemo.dao.TbArticleMapper;
import com.yyl.systemdemo.service.ITbArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
@Service
public class TbArticleServiceImpl extends ServiceImpl<TbArticleMapper, TbArticle> implements ITbArticleService {

}
