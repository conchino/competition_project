package com.competition.project.service;

import com.competition.project.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.competition.project.dto.PermissionDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
public interface PermissionService extends IService<Permission> {
    /* 获取全部权限以及对应权限序号 */
    List<PermissionDTO> getPermissionList();
    /* 根据权限ID获取权限名 */
    String getPermNameById(Integer id);
}
