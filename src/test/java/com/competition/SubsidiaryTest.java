package com.competition;

import com.competition.project.entity.Department;
import com.competition.project.entity.Position;
import com.competition.project.entity.Subsidiary;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.PositionService;
import com.competition.project.service.SubsidiaryService;
import com.competition.project.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class SubsidiaryTest {
    @Autowired
    private SubsidiaryService subsidiaryService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PositionService positionService;

    @Test
    public void getAllSubsidiary(){
        System.out.println(subsidiaryService.getAllSubsidiary());
    }

    @Test
    public void querySubByPage(){
        Map<String, Object> resultMap = subsidiaryService.querySubByPage(1, 5);
        long total = (long) resultMap.get("total");
        List<Subsidiary> records = (List<Subsidiary>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void querySubsidiaryByInPut(){
        Map<String, Object> resultMap = subsidiaryService.querySubsidiaryByInPut("第一", 1, 5);
        long total = (long) resultMap.get("total");
        List<Subsidiary> records = (List<Subsidiary>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void delSubById(){

        String subId = "111";
        if (subsidiaryService.isExistSub(subId)) {
            List<Department> departmentList = departmentService.queryDepartBySub(subId);
            for (Department department : departmentList) {
                List<Position> positionList = positionService.queryPostByDepart(department.getDepartId());
                for (Position position : positionList) {
                    if (!positionService.delPost(position.getPostId())) {
                        System.out.println(position.getPostName()+" 删除失败!");
                    }
                }
                if (!departmentService.delDepartment(department.getDepartId())) {
                    System.out.println(department.getDepartName()+" 删除失败!");
                }
            }
            if (subsidiaryService.delSubById(subId)) {
                System.out.println("公司"+subId+" 删除成功!");
            }else System.out.println("删除失败!");
        }else System.out.println("无对应的公司存在!");
    }
}
