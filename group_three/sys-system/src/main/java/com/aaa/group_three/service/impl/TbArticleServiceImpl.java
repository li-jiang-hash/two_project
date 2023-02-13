package com.aaa.group_three.service.impl;

import com.aaa.entity.TbArticle;
import com.aaa.entity.TbHeadNav;
import com.aaa.group_three.dao.TbArticleMapper;
import com.aaa.group_three.service.ITbArticleService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private TbArticleMapper articleMapper;
    @Override
    public List getArticleById(String navId) {
//        QueryWrapper queryWrapper=new QueryWrapper<>();
//        TbArticle article=new TbArticle();
//        if(article.getNavId()!=null && article.getNavId()!=""){
//            queryWrapper.eq("b.id",article.getNavId());
//        }
        return articleMapper.getArticleById(navId);
    }

    @Override
    public List getArticle(String navId) {

        return articleMapper.getArticle(navId);
    }
}
