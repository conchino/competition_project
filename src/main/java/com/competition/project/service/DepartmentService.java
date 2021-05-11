package com.competition.project.service;

import com.competition.project.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
public interface DepartmentService extends IService<Department> {
    /* 根据部门名称查询部门id */
    String getDepartIdByName(String name);

    /* 根据部门id获取部门名称 */
    String getDepartNameById(String departId);

    /* 根据id判断部门是否存在 */
    Boolean isExistDepartment(String departId);

    /* 分页展示部门 */
    Map<String,Object> queryDepartByPage(long current,long limit);

    /* 根据公司查询部门 */
    Map<String,Object> queryDepartBySub(String belongs,long current, long limit);

    /* 根据公司获取所有部门 */
    List<Department> queryDepartBySub(String belongs);

    /* 添加部门 */
    Boolean addDepartment(String departId,String departName,String belongs);

    /* 删除部门 */
    Boolean delDepartment(String departId);

    /* 修改部门 */
    Boolean updateDepartInfo(String upId,String departId,String departName);

    /* 查询部门 */
    Map<String,Object> queryDepartmentByInPut(String info,long current,long limit);

    /* 查找所有部门号 */
    List<String> getAllDepartmentId();
}
