package com.competition.project.service;

import com.competition.project.dto.PositionDTO;
import com.competition.project.entity.Position;
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
public interface PositionService extends IService<Position> {
    /* 根据职位名称查询部门id */
    String getDepartIdByName(String name);
    /* 根据职位 id 查询职位名称 */
    String getPostNameById(String postId);
    /* 根据部门查看职位 */
    Map<String,Object> queryPostByDepart(String depart,long current,long limit);
    List<Position> queryPostByDepart(String depart);
    /* 删除职位 */
    Boolean delPost(String postId);
    /* 获取所有职位 */
    List<PositionDTO> getAllPost();
}
