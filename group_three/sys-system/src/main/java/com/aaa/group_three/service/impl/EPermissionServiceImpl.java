package com.aaa.group_three.service.impl;

import com.aaa.entity.EPermission;
import com.aaa.entity.RRolePermission;
import com.aaa.group_three.dao.EPermissionMapper;
import com.aaa.group_three.service.IEPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 左侧菜单地址 服务实现类
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@Service
public class EPermissionServiceImpl extends ServiceImpl<EPermissionMapper, EPermission> implements IEPermissionService {

    @Resource
    private EPermissionMapper permissionMapper;
    @Override
    public List<EPermission> getAllMenus(String rid) {

        List<EPermission> permission = permissionMapper.getPermission(rid);
        for (EPermission ePermission : permission) {

            if(ePermission.getPid()==0){

                System.out.println(" = ））））））））））））））））））））））））））））））））" );
                //查询 对应的子菜单
                List<EPermission> sonp =  permissionMapper.getsonPermission(rid,ePermission.getId());
                ePermission.setChildren(sonp);
            }
        }

        return permission;
    }
}
