package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbBottomArticle;
import com.yyl.systemdemo.dao.TbBottomArticleMapper;
import com.yyl.systemdemo.service.ITbBottomArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
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
public class TbBottomArticleServiceImpl extends ServiceImpl<TbBottomArticleMapper, TbBottomArticle> implements ITbBottomArticleService {

    @Override
    public Page getPageData(PageInfo page, TbBottomArticle bottomArticle) {

        System.out.println("bottomArticle的值："+bottomArticle);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(bottomArticle.getName())){
            queryWrapper.like("name",bottomArticle.getName());
        }

        return this.page(page,queryWrapper);
    }
}
