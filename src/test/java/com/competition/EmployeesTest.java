package com.competition;

import com.competition.project.entity.Employees;
import com.competition.project.service.EmployeesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class EmployeesTest {
    @Autowired
    private EmployeesService employeesService;

    @Test
    public void queryEmployeesByPage(){
        Map<String, Object> resultMap = employeesService.queryEmployeesByPage(1, 5);
        long total = (long) resultMap.get("total");
        List<Employees> records = (List<Employees>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void queryUnassignedEmployeesByPage(){
        Map<String, Object> map = employeesService.queryUnassignedEmployeesByPage(1L, 5L);
        long total = (long) map.get("total");
        List<Employees> records = (List<Employees>) map.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void queryEmployeesBySub(){
        Map<String, Object> map = employeesService.queryEmployeesBySub(1, 5, "001");
        List<Employees> records = (List<Employees>) map.get("records");
        long total = (long) map.get("total");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void queryEmployeesByType(){
        Map<String, Object> resultMap = employeesService.queryEmployeesByType(1, 5, "depart", "101");
        long total = (long) resultMap.get("total");
        List<Employees> records = (List<Employees>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }

    @Test
    public void queryEmployeesByInPut(){
        Map<String, Object> resultMap = employeesService.queryEmployeesByInPut(1, 5, "第一");
        long total = (long) resultMap.get("total");
        List<Employees> records = (List<Employees>) resultMap.get("records");
        System.out.println("总记录:  "+total);
        System.out.println("记录:  ");
        records.forEach(System.out::println);
    }


    @Test
    public void updateEmployees(){
        System.out.println(employeesService.updateEmployees("666666"));
    }

    @Test
    public void statisticsDepartmentEmployees(){
        Integer integer = employeesService.statisticsDepartmentEmployees("101");
        System.out.println(integer);
    }

    @Test
    public void statisticsEmployeesCount(){
        System.out.println(employeesService.statisticsEmployeesCount());
    }

    @Test
    public void dispatchStaff(){
        if (employeesService.dispatchStaff("148357","112","303","003")) {
            System.out.println("success~~");
        }
    }
}
