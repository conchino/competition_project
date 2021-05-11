package com.competition;

import com.competition.project.entity.Department;
import com.competition.project.entity.Employees;
import com.competition.project.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class DepartmentTest {
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void queryDepartmentByInPut(){
        Map<String, Object> resultMap = departmentService.queryDepartmentByInPut("开发", 1, 5);
        long total = (long) resultMap.get("total");
        List<Department> records = (List<Department>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void isExistDepartment(){
        Boolean flag = departmentService.isExistDepartment("852651651");
        System.out.println(flag);
    }

    @Test
    public void getAllDepartmentId(){
        System.out.println(departmentService.getAllDepartmentId());
    }
}
