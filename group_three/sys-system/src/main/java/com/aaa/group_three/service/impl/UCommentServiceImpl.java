package com.aaa.group_three.service.impl;

import com.aaa.entity.UComment;
import com.aaa.group_three.dao.UCommentMapper;
import com.aaa.group_three.service.IUCommentService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
@Service
public class UCommentServiceImpl extends ServiceImpl<UCommentMapper, UComment> implements IUCommentService {

    @Resource
    private UCommentMapper commentMapper;

    @Override
    public Page<UComment> findComment(String id, String userId, PageInfo pageInfo) {
        return commentMapper.findComment(id, userId, pageInfo);
    }
}
