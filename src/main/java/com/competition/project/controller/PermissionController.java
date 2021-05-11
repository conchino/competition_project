package com.competition.project.controller;


import com.competition.project.service.PermissionService;
import com.competition.project.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("获取所有权限类型")
    @RequestMapping("/getAllPermission")
    public Result getAllPermission(){
        return Result.ok().message("所有权限").data("permissionList",permissionService.getPermissionList());
    }
}

