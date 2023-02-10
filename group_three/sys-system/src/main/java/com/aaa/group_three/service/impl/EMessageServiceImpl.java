package com.aaa.group_three.service.impl;

import com.aaa.entity.EMessage;
import com.aaa.group_three.dao.EMessageMapper;
import com.aaa.group_three.service.IEMessageService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 系统通知表 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-12
 */
@Service
public class EMessageServiceImpl extends ServiceImpl<EMessageMapper, EMessage> implements IEMessageService {

    @Resource
    private EMessageMapper eMessageMapper;
    @Override
    public Page getPageData(PageInfo page, EMessage message) {
        System.out.println("empInfo的值："+message);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(message.getStype())){
            queryWrapper.like("ename",message.getStype());
        }

//        return this.page(page,queryWrapper);
        return eMessageMapper.getRole(page,queryWrapper);
    }
}
