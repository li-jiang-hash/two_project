package com.aaa.group_three.dao;

import com.aaa.entity.TbArticle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
public interface TbArticleMapper extends BaseMapper<TbArticle> {

    List getArticleById(String navId);

    List getArticle(String navId);
}
