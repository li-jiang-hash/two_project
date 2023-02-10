package com.aaa.group_three.service;

import com.aaa.entity.UComment;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
public interface IUCommentService extends IService<UComment> {

    Page<UComment> findComment(String id, PageInfo pageInfo);
}
