package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.dto.DepartmentDTO;
import com.competition.project.entity.Department;
import com.competition.project.entity.Employees;
import com.competition.project.entity.Position;
import com.competition.project.entity.Subsidiary;
import com.competition.project.mapper.DepartmentMapper;
import com.competition.project.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.competition.project.service.EmployeesService;
import com.competition.project.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Lazy
    @Autowired
    private DepartmentService departmentService;
    @Lazy
    @Autowired
    private EmployeesService employeesService;
    @Lazy
    @Autowired
    private PositionService positionService;

    @Override
    public String getDepartIdByName(String name) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.like("depart_name",name);
        Department department = departmentService.getOne(wrapper);
        return (department==null)?name:department.getDepartId();
    }

    @Override
    public String getDepartNameById(String departId) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.select("depart_name").eq("depart_id",departId);
        Department department = departmentService.getOne(wrapper);
        return department.getDepartName();
    }

    @Override
    public Boolean isExistDepartment(String departId) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("depart_id",departId);
        int count = departmentService.count(wrapper);
        return (count>0);
    }

    @Override
    public Map<String, Object> queryDepartByPage(long current, long limit) {
        Page<Department> departmentPage = new Page<>(current, limit);
        Page<Department> resultPage = departmentService.page(departmentPage);
        Map<String, Object> map = new HashMap<>();
        List<DepartmentDTO> dtoArrayList = new ArrayList<>();

        for (Department department : resultPage.getRecords()){
            dtoArrayList.add(new DepartmentDTO(department));
        }

        map.put("total",resultPage.getTotal());
        map.put("records",dtoArrayList);
        return map;
    }

    @Override
    public Map<String, Object> queryDepartBySub(String belongs,long current, long limit) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs",belongs);
        Page<Department> departmentPage = new Page<>(current,limit);
        Page<Department> resultPage = departmentService.page(departmentPage, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public List<Department> queryDepartBySub(String belongs) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("belongs",belongs);
        return departmentService.list(wrapper);
    }

    @Override
    public Boolean addDepartment(String departId, String departName, String belongs) {
        return departmentService.save(new Department(departId,departName,belongs));
    }

    @Override
    public Boolean delDepartment(String departId) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("depart_id",departId);
        return departmentService.remove(wrapper);
    }

    @Override
    public Boolean updateDepartInfo(String upId, String departId, String departName) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.eq("depart_id",upId);
//        boolean flag1 = employeesService.update(new Employees(departId),new QueryWrapper<Employees>().eq("depart",departId));
//        boolean flag2 = positionService.update(new Position(departId),new QueryWrapper<Position>().eq("depart",departId));
//        System.out.println("employ -- "+flag1+"position -- "+flag2);
        return departmentService.update(new Department(departId,departName),wrapper);
    }

    @Override
    public Map<String, Object> queryDepartmentByInPut(String info, long current, long limit) {
        Page<Department> departmentPage = new Page<>(current, limit);
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.like("depart_id",info)
               .or().like("depart_name",info);
        Page<Department> resultPage = departmentService.page(departmentPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public List<String> getAllDepartmentId() {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.select("depart_id");
        List<Department> departmentList = departmentService.list(wrapper);
        List<String> list = new ArrayList<>();
        for (Department department : departmentList) {
            list.add(department.getDepartId());
        }
        return list;
    }
}
