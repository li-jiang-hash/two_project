package com.aaa.group_three.dao;

import com.aaa.entity.TbBottomArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-19
 */
public interface TbBottomArticleMapper extends BaseMapper<TbBottomArticle> {

    List<Map<String, Object>> getArticle();
}
