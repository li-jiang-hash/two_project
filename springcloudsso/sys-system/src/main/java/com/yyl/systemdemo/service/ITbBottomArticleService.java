package com.yyl.systemdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.TbBottomArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.util.PageInfo;

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
