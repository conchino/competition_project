package com.competition.project.service;

import com.competition.project.dto.SubsidiaryDTO;
import com.competition.project.entity.Subsidiary;
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
public interface SubsidiaryService extends IService<Subsidiary> {
    /* 返回所有公司的信息 */
    List<SubsidiaryDTO> getAllSubsidiary();
    /* 返回公司总数 */
    Integer getSubsidiaryCount();
    /* 查询有无Id对应公司存在 */
    Boolean isExistSub(String subId);
    /* 根据子公司名称查询部门id */
    String getSubIdByName(String name);
    /* 根据公司id获取公司名称 */
    String getSubNameById(String subId);
    /* 分页展示子公司 */
    Map<String,Object> querySubByPage(long current, long limit);
    /* 增加子公司 */
    Boolean addSubsidiary(String subId,String subName);
    /* 删除子公司 */
    Boolean delSubById(String subId);
    /* 修改子公司信息 */
    Boolean updateSubInfo(String upId,String subId,String subName);
    /* 查询子公司 */
    Map<String,Object> querySubsidiaryByInPut(String info,long current,long limit);
}
