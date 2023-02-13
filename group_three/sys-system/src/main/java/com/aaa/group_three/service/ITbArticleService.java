package com.aaa.group_three.service;

import com.aaa.entity.TbArticle;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
public interface ITbArticleService extends IService<TbArticle> {

    List getArticleById(String navId);

    List getArticle(String navId);
}
