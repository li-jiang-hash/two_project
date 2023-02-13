package com.aaa.group_three.service;

import com.aaa.entity.UCollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanglei
 * @since 2023-02-10
 */
public interface IUCollectionService extends IService<UCollection> {

    List<Map<String,Object>> findAll(String uid);

    List<Map<String,Object>> findGoods(String uid);
}
