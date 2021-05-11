package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.entity.Employees;
import com.competition.project.mapper.EmployeesMapper;
import com.competition.project.service.DepartmentService;
import com.competition.project.service.EmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.competition.project.service.PositionService;
import com.competition.project.service.SubsidiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees> implements EmployeesService {
    @Autowired
    @Lazy
    private EmployeesService employeesService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SubsidiaryService subsidiaryService;
    @Autowired
    private PositionService positionService;

    @Override
    public List<Employees> selectAllEmployees() {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.select("work_id","name","sex","age","work_age","post","depart","company",
                "phone","email","address");
        wrapper.ne("name","admin");
        List<Employees> employeesList = employeesService.list(wrapper);
        return employeesList;
    }

    @Override
    public Employees selectEmployeesById(String workId) {
        Employees employe = employeesService.getOne(new QueryWrapper<Employees>().eq("work_id", workId));
        return employe;
    }

    @Override
    public boolean isExistEmployees(String workId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("work_id",workId);
        int count = employeesService.count(wrapper);
        return (count > 0);
    }

    @Override
    public String getNameByWorkId(String workId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.select("name").eq("work_id",workId);
        Employees employees = employeesService.getOne(wrapper);
        return employees.getName();
    }

    @Override
    public String getMailByWorkId(String workId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.select("email").eq("work_id",workId);
        Employees employees = employeesService.getOne(wrapper);
        return employees.getEmail();
    }

    @Override
    public Map<String, Object> queryEmployeesByPage(long current, long limit) {
        Page<Employees> employeesPage = new Page<>(current,limit);
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.ne("name","admin");
        Page<Employees> resultPage = employeesService.page(employeesPage,wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> queryUnassignedEmployeesByPage(long current, long limit) {
        Page<Employees> employeesPage = new Page<>(current,limit);
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.ne("name","admin")
                .eq("post","未分配");
        Page<Employees> resultPage = employeesService.page(employeesPage,wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> queryEmployeesBySub(long current, long limit, String company) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("company",company);
        Page<Employees> employeesPage = new Page<>(current,limit);
        Page<Employees> resultPage = employeesService.page(employeesPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> queryEmployeesByDepart(long current, long limit, String depart) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("depart",depart);
        Page<Employees> employeesPage = new Page<>(current,limit);
        Page<Employees> resultPage = employeesService.page(employeesPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public List<Employees> queryEmployeesByDepart(String depart) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("depart",depart);
        return employeesService.list(wrapper);
    }

    @Override
    public Map<String, Object> queryEmployeesByPost(long current, long limit, String post) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("post",post);
        Page<Employees> employeesPage = new Page<>(current, limit);
        Page<Employees> resultPage = employeesService.page(employeesPage,wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> queryEmployeesByType(long current, long limit, String type, String value) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq(type,value);
        Page<Employees> employeesPage = new Page<>(current, limit);
        Page<Employees> resultPage = employeesService.page(employeesPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> queryEmployeesByInPut(long current, long limit, String info) {
        Page<Employees> employeesPage = new Page<>(current,limit);

        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.ne("name","admin");     // 禁查管理员
        if (!info.isEmpty()){
            wrapper.and(qw -> qw.like("work_id",info)
                    .or().like("name",info)
                    .or().eq("sex",info)
                    .or().eq("age",info)
                    .or().eq("work_age",info)
                    .or().like("post",info)
                    .or().eq("post",positionService.getDepartIdByName(info))
                    .or().like("depart",info)
                    .or().eq("depart",departmentService.getDepartIdByName(info))
                    .or().like("company",info)
                    .or().eq("company",subsidiaryService.getSubIdByName(info))
                    .or().like("phone",info)
                    .or().like("email",info)
                    .or().like("address",info));
        }
        Page<Employees> resultPage = employeesService.page(employeesPage,wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Boolean updateEmployees(String workId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("work_id",workId);
        Employees employees = employeesService.getOne(wrapper);
        employees.setPost(null);
        employees.setDepart(null);
        employees.setCompany(null);
        return employeesService.update(employees,wrapper);
    }

    @Override
    public Integer statisticsSubsidiaryEmployees(String subId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("company", subId);
        return employeesService.count(wrapper);
    }

    @Override
    public Integer statisticsDepartmentEmployees(String depart) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("depart",depart);
        return employeesService.count(wrapper);
    }

    @Override
    public Integer statisticsPositionEmployees(String positionId) {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.eq("post", positionId);
        return employeesService.count(wrapper);
    }

    @Override
    public Integer statisticsEmployeesCount() {
        QueryWrapper<Employees> wrapper = new QueryWrapper<>();
        wrapper.select("count(*) count").groupBy("company");
//        List<Map<String, Object>> list = baseMapper.selectMaps(wrapper);
        List<Map<String, Object>> mapList = employeesService.listMaps(wrapper);
        ArrayList<Integer> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(Integer.valueOf((map.get("count").toString())));
        }
        return Collections.max(list);
    }
}
