package com.competition.project.service;

import com.competition.project.entity.Employees;
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
public interface EmployeesService extends IService<Employees> {
    /*
        查询所有员工
     */
    List<Employees> selectAllEmployees();
    /* 根据工号查询员工 */
    Employees selectEmployeesById(String workId);

    /* 根据工号判断是否存在对应的员工 */
    boolean isExistEmployees(String workId);

    /* 根据工号获取姓名 */
    String getNameByWorkId(String workId);

    /* 根据工号获取邮箱 */
    String getMailByWorkId(String workId);

    /* 分页展示员工信息 */
    Map<String,Object> queryEmployeesByPage(long current,long limit);

    Map<String, Object> queryUnassignedEmployeesByPage(long current, long limit);

    /* 根据子公司筛选员工 */
    Map<String,Object> queryEmployeesBySub(long current,long limit,String company);

    /* 根据部门查找员工 */
    Map<String,Object> queryEmployeesByDepart(long current,long limit,String depart);
    List<Employees> queryEmployeesByDepart(String depart);

    /* 根据职位筛选员工 */
    Map<String,Object> queryEmployeesByPost(long current,long limit,String post);

    /* 根据传入的字段以及对应的值及进行查找 */
    Map<String,Object> queryEmployeesByType(long current,long limit,String val,String type);

    /* 全局全域搜索 */
    Map<String,Object> queryEmployeesByInPut(long current,long limit,String info);

    /* 修改员工职位部门和公司 */
    Boolean updateEmployees(String workId);

    /* 统计一个公司下所有员工 */
    Integer statisticsSubsidiaryEmployees(String subId);

    /* 统计同一个部门的所有员工 */
    Integer statisticsDepartmentEmployees(String depart);

    /* 统计同属一个职位的所有员工 */
    Integer statisticsPositionEmployees(String positionId);

    /* 统计子公司最大员工量 */
    Integer statisticsEmployeesCount();
    /* 调度员工 */
    Boolean dispatchStaff(String workId, String post, String depart, String company);
}
