package com.yyl.systemdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyl.entity.RRolePermission;
import com.yyl.systemdemo.dao.RRolePermissionMapper;
import com.yyl.systemdemo.service.IRRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 角色权限连表 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2023-01-08
 */
@Service
public class RRolePermissionServiceImpl extends ServiceImpl<RRolePermissionMapper, RRolePermission> implements IRRolePermissionService {


    @Resource
    private RRolePermissionMapper rolePermissionMapper;
    @Override
    public boolean updPermission(String roleId, String menuids) {

        System.out.println("对应的权限的id的值是："+menuids);
        //1.删除一下这个角色之前的权限
        QueryWrapper<RRolePermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        int delete=rolePermissionMapper.delete(queryWrapper);
        System.out.println("删除的行数是："+delete);
        //2.将新的权限id添加到数据库里面
        //数组里面的数据通过“,”分割
        String[]perid=menuids.split(",");
        for(String s : perid){
            RRolePermission rRolePermission=new RRolePermission();
            String id=System.currentTimeMillis()+"";//当前时间的毫秒数
            rRolePermission.setId(id);
            rRolePermission.setRoleId(roleId);
            rRolePermission.setPermissionId(s);
            rolePermissionMapper.insert(rRolePermission);
        }
        return true;
    }
}
