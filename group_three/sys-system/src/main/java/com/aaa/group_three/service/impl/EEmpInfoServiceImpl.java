package com.aaa.group_three.service.impl;

import com.aaa.entity.EEmpInfo;
import com.aaa.group_three.dao.EEmpInfoMapper;
import com.aaa.group_three.service.IEEmpInfoService;
import com.aaa.util.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-10
 */
@Service
public class EEmpInfoServiceImpl extends ServiceImpl<EEmpInfoMapper, EEmpInfo> implements IEEmpInfoService {
    @Resource
    private EEmpInfoMapper empInfoMapper;

    @Override
    public Page getPageData(PageInfo page, EEmpInfo empInfo) {
        System.out.println("empInfo的值："+empInfo);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // "" null
        if(StringUtils.isNotBlank(empInfo.getEname())){
            queryWrapper.like("ename",empInfo.getEname());
        }

        return this.page(page,queryWrapper);
    }

    @Override
    public List<EEmpInfo> getById(Integer id) {
        return empInfoMapper.getRoleById(id);
    }
}
