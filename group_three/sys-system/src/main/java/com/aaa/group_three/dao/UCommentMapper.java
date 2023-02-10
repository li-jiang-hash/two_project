package com.aaa.group_three.dao;

import com.aaa.entity.UComment;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
public interface UCommentMapper extends BaseMapper<UComment> {

    Page<UComment> findComment(String id, String userId, PageInfo pageInfo);
}
