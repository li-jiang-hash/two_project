package com.aaa.group_three.service.impl;

import com.aaa.entity.UCollection;
import com.aaa.group_three.dao.UCollectionMapper;
import com.aaa.group_three.service.IUCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
@Service
public class UCollectionServiceImpl extends ServiceImpl<UCollectionMapper, UCollection> implements IUCollectionService {
@Resource
private UCollectionMapper uCollectionMapper;


    @Override
    public List<Map<String, Object>> findAll(String uid) {
        return uCollectionMapper.findAll(uid);
    }

    @Override
    public List<Map<String, Object>> findGoods(String uid) {
        return uCollectionMapper.findGoods(uid);
    }
}
