package com.competition.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.dto.SubsidiaryDTO;
import com.competition.project.entity.Subsidiary;
import com.competition.project.mapper.SubsidiaryMapper;
import com.competition.project.service.SubsidiaryService;
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
public class SubsidiaryServiceImpl extends ServiceImpl<SubsidiaryMapper, Subsidiary> implements SubsidiaryService {
    @Autowired
    @Lazy
    private SubsidiaryService subsidiaryService;

    @Override
    public List<SubsidiaryDTO> getAllSubsidiary() {
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.ne("sub_name","未分配").select("sub_id","sub_name");
        List<Subsidiary> subsidiaryList = subsidiaryService.list(wrapper);
        ArrayList<SubsidiaryDTO> subsidiaryDTOArrayList = new ArrayList<>();
        for (Subsidiary subsidiary : subsidiaryList) {
            subsidiaryDTOArrayList.add(new SubsidiaryDTO(subsidiary));
        }
        return subsidiaryDTOArrayList;
    }

    @Override
    public Integer getSubsidiaryCount() {
        return subsidiaryService.count();
    }

    @Override
    public Boolean isExistSub(String subId){
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_id",subId);
        int count = subsidiaryService.count(wrapper);
        return (count>0);
    }

    @Override
    public String getSubIdByName(String name) {
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.like("sub_name",name);
        Subsidiary subsidiary = subsidiaryService.getOne(wrapper);
        return (subsidiary==null)?name:subsidiary.getSubId();
    }

    @Override
    public String getSubNameById(String subId) {
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.select("sub_name").eq("sub_id",subId);
        Subsidiary subsidiary = subsidiaryService.getOne(wrapper);
        return subsidiary.getSubName();
    }

    @Override
    public Map<String, Object> querySubByPage(long current, long limit) {
        Page<Subsidiary> subsidiaryPage = new Page<>(current, limit);
        Page<Subsidiary> resultPage = subsidiaryService.page(subsidiaryPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

    @Override
    public Boolean addSubsidiary(String subId, String subName) {
        return subsidiaryService.save(new Subsidiary(subId,subName));
    }

    @Override
    public Boolean delSubById(String subId) {
            QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
            wrapper.eq("sub_id",subId);
            return subsidiaryService.remove(wrapper);
    }

    @Override
    public Boolean updateSubInfo(String upId,String subId, String subName) {
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_id",upId);
        return subsidiaryService.update(new Subsidiary(subId,subName),wrapper);
    }

    @Override
    public Map<String, Object> querySubsidiaryByInPut(String info, long current, long limit) {
        Page<Subsidiary> subsidiaryPage = new Page<>(current,limit);
        QueryWrapper<Subsidiary> wrapper = new QueryWrapper<>();
        wrapper.like("sub_id",info)
               .or().like("sub_name",info);
        Page<Subsidiary> resultPage = subsidiaryService.page(subsidiaryPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total",resultPage.getTotal());
        map.put("records",resultPage.getRecords());
        return map;
    }

}
