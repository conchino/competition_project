package com.competition.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.competition.project.dto.EmployeesDTO;
import com.competition.project.entity.Employees;
import com.competition.project.service.*;
import com.competition.project.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project/login-account")
public class LoginAccountController {
    @Autowired
    private EmployeesService employeesService;
    @Autowired
    private LoginAccountService loginAccountService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SubsidiaryService subsidiaryService;


    // 申请账号
    @RequestMapping(value = "applyAccount",method = RequestMethod.POST)
    public Result applyLoginAccount(@RequestBody JSONObject jsonObject) {
        String workId = (String) jsonObject.get("workId");
        String password = (String) jsonObject.get("password");
        String perm = (String) jsonObject.get("perm");
        String name = (String) jsonObject.get("name");
        String mail = (String) jsonObject.get("mail");

        System.out.println("-------账号申请-------");
        System.out.println("workId: " + workId + "\npassword: " + password + "\nperm: " + perm + "\nname: " + name + "\nmail: " + mail);


        // 首先根据工号查询员工，判断是否存在该工号对应的员工
        if (employeesService.isExistEmployees(workId)) {
            if (employeesService.getNameByWorkId(workId).equals(name)) {
                if (employeesService.getMailByWorkId(workId).equals(mail)) {
                    // 再判断该员工是否已存在账号
                    if (loginAccountService.notExistAccount(workId)) {
                        if (loginAccountService.applyLoginAccount(workId, password, Integer.valueOf(perm))) {
                            String account = loginAccountService.queryAccountByWorkId(workId);
                            // 返回创建成功的账号
                            Map<String, Object> map = new HashMap<>();
                            map.put("account", account);
                            System.out.println("account: "+account);
                            return Result.ok().message("账号创建成功 !").data(map);
                        }
                        return Result.error().message("账号创建失败 !");
                    }
                    return Result.error().message("该用户已存在账号 !");
                }
                return Result.error().message("邮箱错误！");
            }
            return Result.error().message("姓名不正确 !");
        }
        return Result.error().message("不存在该工号 !");

    }


    // 查询待审核账号
    @RequiresPermissions("user:perm_1")     // 需要权限一
    @RequestMapping("showPendingAccounts")
    public Result showPendingAccounts(@RequestParam("current") Long current,
                                      @RequestParam("limit") Long limit) {
        List<Map<String, Object>> mapList = loginAccountService.queryPendingAccounts();
        if (!mapList.isEmpty()) {
            for (Map<String, Object> map : mapList) {
                Employees employees = employeesService.selectEmployeesById(String.valueOf(map.get("workId")));
                EmployeesDTO employeesDTO = new EmployeesDTO(employees);
                employeesDTO.setPost(positionService.getPostNameById(employees.getPost()));
                employeesDTO.setDepart(departmentService.getDepartNameById(employees.getDepart()));
                employeesDTO.setCompany(subsidiaryService.getSubNameById(employees.getCompany()));

                map.put("applyInfo",employeesDTO);
            }
            return Result.ok().data("accountList", mapList);
        }
        return Result.error().message("查询结果为空!");
    }


    // 同意账号申请
    @RequiresPermissions("user:perm_1")
    @RequestMapping(value = "agreeApply",method = RequestMethod.GET)
    public Result agreeApply(@RequestParam("account") String account) {
//        String account = String.valueOf(jsonObject.get("account"));
        if (loginAccountService.toExamineAccount(account)) {
            return Result.ok().message("账号审核通过！");
        }
        return Result.error().message("审核请求失败！");
    }

    // 拒绝账号申请
    @RequiresPermissions("user:perm_1")
    @RequestMapping("unAgreeApply")
    public Result unAgreeApply(@Param("account") String account) {
        if (loginAccountService.notAllowAccount(account)) {
            return Result.ok().message("审核拒绝请求成功 ！");
        }
        return Result.error().message("审核拒绝请求失败 ！");
    }

    @RequiresPermissions("user:perm_1")
    @RequestMapping("countAssignAccount")
    public Result countAssignAccount(){
        return Result.ok().data("dataValue",loginAccountService.countAssignAccount()*100);
    }

    @RequestMapping("getPermByWorkId")
    public Result getPermByWorkId(@RequestParam("workId") String workId){
        return Result.ok().data("perm",loginAccountService.getPermByWorkId(workId));
    }
}

