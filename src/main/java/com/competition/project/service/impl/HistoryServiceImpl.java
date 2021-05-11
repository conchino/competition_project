package com.competition.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.competition.project.entity.History;
import com.competition.project.mapper.HistoryMapper;
import com.competition.project.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author conchino
 * @since 2021-05-10
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    @Lazy
    HistoryService historyService;

    @Override
    public Boolean addHistoryRecords(History history) {
        return historyService.save(history);
    }

    @Override
    public List<History> queryAllHistory() {
        return historyService.list();
    }

    @Override
    public Map<String, Object> queryHistory(Long current, Long limit) {
        Page<History> page = new Page<>(current, limit);
        Page<History> historyPage = historyService.page(page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("records", historyPage.getRecords());
        map.put("total", historyPage.getTotal());
        return map;
    }
}
