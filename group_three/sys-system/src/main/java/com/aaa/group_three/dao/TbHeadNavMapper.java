package com.aaa.group_three.dao;

import com.aaa.entity.TbHeadNav;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-14
 */
public interface TbHeadNavMapper extends BaseMapper<TbHeadNav> {

    Page getAll(Page page, @Param("headNav") TbHeadNav headNav);
}
