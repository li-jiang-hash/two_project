package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyl.entity.EPermission;
import com.yyl.entity.ERole;
import com.yyl.entity.RRolePermission;
import com.yyl.systemdemo.dao.EPermissionMapper;
import com.yyl.systemdemo.dao.ERoleMapper;
import com.yyl.systemdemo.dao.RRolePermissionMapper;
import com.yyl.systemdemo.service.IERoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@Service
public class ERoleServiceImpl extends ServiceImpl<ERoleMapper, ERole> implements IERoleService {

    @Resource
    private EPermissionMapper permissionMapper;
    @Resource
    private RRolePermissionMapper rolePermissionMapper;
    @Override
    public Page getPageData(PageInfo page, ERole role) {
        System.out.println("role的值："+role);
        System.out.println("page的值："+page);
//        Page page = new Page(currentPage,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(role.getStatus())){
            queryWrapper.eq("status",role.getStatus());
        }
        // "" null
        if(StringUtils.isNotBlank(role.getRoleName())){
            queryWrapper.like("role_name",role.getRoleName());
        }

        return this.page(page,queryWrapper);
    }

    @Override
    public Map<String, Object> getPermissionData(Integer id) {
        //获取所有的菜单，权限的信息
        List<EPermission> allPermission = permissionMapper.getAllPermission();

        //2.获取当前角色所拥有的权限的id
        QueryWrapper<RRolePermission>queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_id",id);
        List<RRolePermission>rRolePermissions=rolePermissionMapper.selectList(queryWrapper);

        List<String>collect=rRolePermissions.stream().map(rRolePermission -> rRolePermission.getPermissionId()).collect(Collectors.toList());

        Map<String,Object>map=new HashMap<>();
        map.put("firstMenus",allPermission);
        map.put("permission",collect);

        return map;
    }



}
