package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.competition.project.entity.Permission;
import com.competition.project.mapper.PermissionMapper;
import com.competition.project.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.competition.project.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Override
    public List<PermissionDTO> getPermissionList() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.select("perm_id","perm_name");
        List<Permission> permissionList = permissionService.list(wrapper);
        List<PermissionDTO> permissionDTOArrayList = new ArrayList<>();
        for (Permission permission : permissionList) {
            permissionDTOArrayList.add(new PermissionDTO(permission.getPermId(),permission.getPermName()));
        }
        return permissionDTOArrayList;
    }

    @Override
    public String getPermNameById(Integer id) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("perm_id",id);
        Permission permission = permissionService.getOne(wrapper);
        return permission.getPermName();
    }
}
