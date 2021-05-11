package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.dto.PositionDTO;
import com.competition.project.entity.Position;
import com.competition.project.mapper.PositionMapper;
import com.competition.project.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    @Autowired
    @Lazy
    private PositionService positionService;

    @Override
    public String getDepartIdByName(String name) {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.like("post_name",name);
        Position position = positionService.getOne(wrapper);
        return (position==null)?name:position.getPostId();
    }

    @Override
    public String getPostNameById(String postId) {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.select("post_name").eq("post_id", postId);
        Position position = positionService.getOne(wrapper);
        return position.getPostName();
    }

    @Override
    public Map<String, Object> queryPostByDepart(String depart,long current,long limit) {
        Page<Position> positionPage = new Page<>(current, limit);
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.eq("depart",depart);
        Page<Position> resultPage = positionService.page(positionPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public List<Position> queryPostByDepart(String depart) {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.eq("depart",depart);
        return positionService.list(wrapper);
    }

    @Override
    public Boolean delPost(String postId) {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id",postId);
        return positionService.remove(wrapper);
    }

    @Override
    public List<PositionDTO> getAllPost() {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.ne("post_name", "未分配");
        List<Position> positionList = positionService.list(wrapper);
        ArrayList<PositionDTO> dtoArrayList = new ArrayList<>();
        for (Position position : positionList) {
            dtoArrayList.add(new PositionDTO(position));
        }
        return dtoArrayList;
    }
}
