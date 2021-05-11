package com.competition.project.controller;


import com.alibaba.fastjson.JSONObject;
import com.competition.project.entity.Department;
import com.competition.project.entity.Employees;
import com.competition.project.entity.Position;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.EmployeesService;
import com.competition.project.service.PositionService;
import com.competition.project.utils.Result;
import com.competition.project.dto.DepartmentDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private EmployeesService employeesService;


    @ApiOperation("分页查找部门")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/queryDepartByPage")
    public Result queryDepartByPage(@RequestParam("current")long current, @RequestParam("limit")long limit){
        return Result.ok().data(departmentService.queryDepartByPage(current,limit));
    }

    @ApiOperation("添加部门")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/addDepartment",method = RequestMethod.GET)
    public Result addDepartment(@RequestParam("departId") String departId,
                                @RequestParam("departName") String departName,
                                @RequestParam("belongs")String belongs){

        if (departmentService.addDepartment(departId,departName,belongs)) {
            return Result.ok().message("创建成功!");
        }
        return Result.error().message("创建失败!");
    }

    @ApiOperation("删除部门")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/delDepartment/{departId}")
    public Result delDepartment(@PathVariable("departId") String departId){
        if (departmentService.isExistDepartment(departId)) {
            if (departmentService.delDepartment(departId)) {
                return Result.ok().message("删除成功!");
            }else return Result.error().message("删除失败!");
        }
        return Result.error().message("不存在该部门!");
    }


    @ApiOperation("更新部门信息")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/updateDepartInfo")
    public Result updateDepartInfo(@RequestParam("upId") String upId,
                                   @RequestParam("departId") String departId,
                                   @RequestParam("departName") String departName){
        if (departmentService.isExistDepartment(upId)) {
            if (departmentService.updateDepartInfo(upId,departId,departName)) {
                return Result.ok().message("修改成功!");
            }else return Result.error().message("修改失败!");
        }
        return Result.error().message("查询不到该部门!");
    }

    @ApiOperation("根据部门筛选员工")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/queryPostEmployByDepart")
    public Result queryPostEmployByDepart(@RequestParam("departId") String departId){
        List<Position> positionList = positionService.queryPostByDepart(departId);
        List<Employees> employeesList = employeesService.queryEmployeesByDepart(departId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("positionList",positionList);
        map.put("employeesList",employeesList);
        return Result.ok().data(map);
    }

    @ApiOperation("查找公司所属部门")
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/queryDepartBySub",method = RequestMethod.GET)
    public Result queryDepartBySub(@RequestParam("subId") String belongs){
        List<Department> departmentList = departmentService.queryDepartBySub(belongs);
        List<DepartmentDTO> departmentDTOArrayList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentDTOArrayList.add(new DepartmentDTO(department));
        }
        return Result.ok().data("departmentList", departmentDTOArrayList);
    }

    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "/queryDepartmentByInPut",method = RequestMethod.GET)
    public Result queryDepartmentByInPut(@RequestParam("info") String info,
                                         @RequestParam("current")long current,
                                         @RequestParam("limit")long limit){
        Map<String, Object> map = departmentService.queryDepartmentByInPut(info, current, limit);
        return Result.ok().data(map);
    }


    @ApiOperation("获取部门员工统计数据")
    @RequiresPermissions("user:perm_1")
    @RequestMapping("/getAllDepartAndEmployees")
    public Result getAllDepartAndEmployees(){
        List<String> departmentIdList = departmentService.getAllDepartmentId();
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (String departmentId : departmentIdList) {
            HashMap<String, Object> map = new HashMap<>();
            Integer integer = employeesService.statisticsDepartmentEmployees(departmentId);
            map.put("value",integer);
            map.put("name",departmentId);
            list.add(map);
        }
        return Result.ok().data("data",list);
    }

}

