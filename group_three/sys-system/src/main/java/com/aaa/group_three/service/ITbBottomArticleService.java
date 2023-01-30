package com.aaa.group_three.service;

import com.aaa.entity.TbBottomArticle;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
public interface ITbBottomArticleService extends IService<TbBottomArticle> {

    Page getPageData(PageInfo page, TbBottomArticle bottomArticle);
}
