package com.competition.project.controller;

import com.competition.project.dto.SubsidiaryDTO;
import com.competition.project.dto.WorkRecordsDTO;
import com.competition.project.entity.Department;
import com.competition.project.entity.Position;
import com.competition.project.entity.WorkRecords;
import com.competition.project.service.*;
import com.competition.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    @Autowired
    private WorkRecordsService workRecordsService;
    @Autowired
    @Lazy
    private LabelClassService labelClassService;

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


    @RequestMapping("getEmployeesRecordsData")
    public Result getEmployeesRecordsData(@RequestParam("workId") String workId){
        List<WorkRecordsDTO> recordsDTOList = workRecordsService.getWorkRecords(workId);
        List<String> labelList = labelClassService.getAllLabelClass();
        List<List<Object>> source = new ArrayList<>();
        Map<String, Object> hashMap = new HashMap<>();

        for (String str : labelList) {
            hashMap.put(str, 0);
            List<Object> strings = new ArrayList<>();
            strings.add(str);
            source.add(strings);
        }


        for (int i = 0; i < recordsDTOList.size(); i+=7) {

            for (int j = i; j < i + 7; j++) {
                WorkRecordsDTO recordsDTO = recordsDTOList.get(j);
                System.out.println(recordsDTO);
                String labelName = labelClassService.getLabelNameById(recordsDTO.getLabelClass());

                if (hashMap.containsKey(labelName)) {
                    hashMap.put(labelName, (int) hashMap.get(labelName) + 1);
                    System.out.println(hashMap);
                }
            }

            for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                for (List<Object> objectList : source) {
                    if (objectList.contains(key)) {
                        objectList.add(value);
                    }
                }
            }
        }

        return Result.ok().data("source",source);
    }


}
