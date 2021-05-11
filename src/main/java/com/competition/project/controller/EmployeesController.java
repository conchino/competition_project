package com.competition.project.controller;


import com.alibaba.fastjson.JSONObject;
import com.competition.project.annotation.Log;
import com.competition.project.dto.EmployeesDTO;
import com.competition.project.entity.Employees;
import com.competition.project.enumeration.OperationType;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.EmployeesService;
import com.competition.project.service.PositionService;
import com.competition.project.service.SubsidiaryService;
import com.competition.project.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
@RequestMapping("/project/employees")
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SubsidiaryService subsidiaryService;

    // 查看所有员工
    @ApiOperation("查看所有员工")
    @RequiresPermissions(value = {"user:perm_1","user:perm_2","user:1","user:2","user:3"},logical = Logical.OR)
    @GetMapping("/selectEmployees")
    public Result QueryAllEmployees(){
        List<Employees> employeesList = employeesService.selectAllEmployees();
        return Result.ok().data("employeesList",employeesList);
    }

    @ApiOperation("分页展示员工")
    @RequiresPermissions("user:perm_1")
    @GetMapping("/queryEmployeesByPage/{current}/{limit}")
    public Result queryEmployeesByPage(@PathVariable("current") String current,
                                       @PathVariable("limit") String limit){
//        long current = (long) jsonObject.get("current");
//        long limit = (long) jsonObject.get("limit");
        long current1 = Long.parseLong(current);
        long limit1 = Long.parseLong(limit);

        Map<String, Object> employees = employeesService.queryEmployeesByPage(current1, limit1);
        return Result.ok().data(employees);
    }

    @ApiOperation("分页展示未分配员工")
    @RequestMapping("queryUnassignedEmployeesByPage/{current}/{limit}")
    public Result queryUnassignedEmployeesByPage(@PathVariable("current") String current,
                                                 @PathVariable("limit") String limit){
        long current1 = Long.parseLong(current);
        long limit1 = Long.parseLong(limit);

        Map<String, Object> employees = employeesService.queryUnassignedEmployeesByPage(current1, limit1);
        return Result.ok().data(employees);
    }

    @RequestMapping("/queryEmployeesBySub")
    public Result queryEmployeesBySub(@RequestParam("company") String company,
                                      @RequestParam("current") long current,
                                      @RequestParam("limit") long limit){
        Map<String, Object> empSub = employeesService.queryEmployeesBySub(current, limit, company);
        return Result.ok().data(empSub);
    }

    @RequestMapping("/queryEmployeesByDepart")
    public Result queryEmployeesByDepart(@RequestParam("depart") String depart,
                                      @RequestParam("current") long current,
                                      @RequestParam("limit") long limit){
        Map<String, Object> employeesByDepart = employeesService.queryEmployeesByDepart(current,limit,depart);
        return Result.ok().data(employeesByDepart);
    }

    @RequestMapping("/queryEmployeesByPost")
    public Result queryEmployeesByPost(@RequestParam("post") String post,
                                         @RequestParam("current") long current,
                                         @RequestParam("limit") long limit){
        Map<String, Object> employeesByPost = employeesService.queryEmployeesByPost(current, limit, post);
        return Result.ok().data(employeesByPost);
    }

    @RequestMapping("/selectEmployeesById")
    public Result selectEmployeesById(@RequestParam("workId") String workId){
        Employees employees = employeesService.selectEmployeesById(workId);
        EmployeesDTO employeesDTO = new EmployeesDTO(employees);
        employeesDTO.setPost(positionService.getPostNameById(employees.getPost()));
        employeesDTO.setDepart(departmentService.getDepartNameById(employees.getDepart()));
        employeesDTO.setCompany(subsidiaryService.getSubNameById(employees.getCompany()));

        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = employeesDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName =  field.getName();
            if(getValueByFieldName(fieldName,employeesDTO)!=null)
                map.put(fieldName,  getValueByFieldName(fieldName,employeesDTO));
        }
        map.put("workAge",employees.getWorkAge());
        return Result.ok().data(map);
    }

    @Log(operationType = OperationType.SELECT, operationName = "模糊查询员工信息")
    @ApiOperation("模糊查询员工信息")
    @RequestMapping(value = "/queryEmployeesByInPut/{current}/{limit}", method=RequestMethod.GET)
    public Result queryEmployeesByInPut(@ApiParam("查询信息") @RequestParam("inputInfo") String inputInfo,
            @ApiParam("当前页面") @PathVariable("current") long current,
            @ApiParam("页面大小") @PathVariable("limit") long limit){
        // POST 方法用
//        String inputInfo = (String) jsonObject.get("inputInfo");
//        System.out.println("inputInfo:  "+inputInfo);
//        String StrCurrent = (String) jsonObject.get("current");
//        String StrLimit = (String) jsonObject.get("limit");
//        long current = Long.parseLong(StrCurrent);
//        long limit = Long.parseLong(StrLimit);
        Map<String, Object> employeesByInPut = employeesService.queryEmployeesByInPut(current, limit, inputInfo);
        return Result.ok().data(employeesByInPut);
    }


    /*  根据对象属性名对应的get 方法获取此类的属性值  */
    private static Object getValueByFieldName(String fieldName,Object object) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(object, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }


}

