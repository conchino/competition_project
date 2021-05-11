package com.competition.project.service;

import com.competition.project.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author conchino
 * @since 2021-05-10
 */
public interface HistoryService extends IService<History> {
    /* 添加历史操作 */
    Boolean addHistoryRecords(History history);
    /* 查找所有 */
    List<History> queryAllHistory();
    /* 查找历史操作 */
    Map<String, Object> queryHistory(Long current, Long limit);
}
