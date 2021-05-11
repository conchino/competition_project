package com.competition.project.controller.testcontroller;

import com.alibaba.fastjson.JSONObject;
import com.competition.project.entity.Department;
import com.competition.project.entity.Position;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.PositionService;
import com.competition.project.service.SubsidiaryService;
import com.competition.project.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private SubsidiaryService subsidiaryService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;

    @ApiOperation("删除公司并递归删除其下部门职位")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/test/delSubById",method = RequestMethod.GET)
    public Result delSubById(@RequestBody JSONObject jsonObject){
        String subId = String.valueOf(jsonObject.get("subId"));

        if (subsidiaryService.isExistSub(subId)) {
            List<Department> departmentList = departmentService.queryDepartBySub(subId);
            for (Department department : departmentList) {
                List<Position> positionList = positionService.queryPostByDepart(department.getDepartId());
                for (Position position : positionList) {
                    if (!positionService.delPost(position.getPostId())) {
                        return Result.error().message(position.getPostName()+" 删除失败!");
                    }
                }
                if (!departmentService.delDepartment(department.getDepartId())) {
                    return Result.error().message(department.getDepartName()+" 删除失败!");
                }
            }
            if (subsidiaryService.delSubById(subId)) {
                return Result.ok().message("公司"+subId+" 删除成功!");
            }else Result.error().message("删除失败!");
        }
        return Result.error().message("无对应的公司存在!");
    }
}
