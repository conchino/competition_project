package com.competition.project.controller;

import com.competition.project.dto.SubsidiaryDTO;
import com.competition.project.entity.Department;
import com.competition.project.entity.Position;
import com.competition.project.service.*;
import com.competition.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class InfoDataController {
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

    @RequestMapping("AllInfo")
    public Result getAllInfo(){
        List<SubsidiaryDTO> allSubsidiary = subsidiaryService.getAllSubsidiary();
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (SubsidiaryDTO subsidiaryDTO : allSubsidiary) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",subsidiaryDTO.getSubName());
            List<Map<String, Object>> departList = new ArrayList<>();

            for (Department department : departmentService.queryDepartBySub(subsidiaryDTO.getSubId())) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("name", department.getDepartName());
                map1.put("value", employeesService.statisticsDepartmentEmployees(department.getDepartId()));

                List<Map<String, Object>> positionList = new ArrayList<>();

                for (Position position : positionService.queryPostByDepart(department.getDepartId())) {
                    HashMap<String, Object> map2 = new HashMap<>();
                    map2.put("name", position.getPostName());
                    map2.put("value", employeesService.statisticsPositionEmployees(position.getPostId()));

                    positionList.add(map2);
                }
                map1.put("children", positionList);
                departList.add(map1);
            }
            map.put("children", departList);
            mapList.add(map);
        }
        return Result.ok().message(employeesService.statisticsEmployeesCount().toString()).data("result", mapList);
    }


    @RequestMapping("CheckboxValue")
    public Result CheckboxValue(){
        List<SubsidiaryDTO> allSubsidiary = subsidiaryService.getAllSubsidiary();
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (SubsidiaryDTO subsidiaryDTO : allSubsidiary) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label",subsidiaryDTO.getSubName());
            map.put("value", subsidiaryDTO.getSubId());

            List<Map<String, Object>> departList = new ArrayList<>();
            for (Department department : departmentService.queryDepartBySub(subsidiaryDTO.getSubId())) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("label", department.getDepartName());
                map1.put("value",department.getDepartId());

                List<Map<String, Object>> positionList = new ArrayList<>();

                for (Position position : positionService.queryPostByDepart(department.getDepartId())) {
                    HashMap<String, Object> map2 = new HashMap<>();
                    map2.put("label", position.getPostName());
                    map2.put("value", position.getPostId());
                    positionList.add(map2);
                }
                map1.put("children", positionList);
                departList.add(map1);
            }
//            map.put("id", subsidiaryDTO.getSubId());
            map.put("children", departList);
            mapList.add(map);
        }
        return Result.ok().message(employeesService.statisticsEmployeesCount().toString()).data("result", mapList);
    }

}
