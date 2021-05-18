package com.competition.project.controller;


import com.competition.project.annotation.Log;
import com.competition.project.dto.HistoryDTO;
import com.competition.project.entity.History;
import com.competition.project.enumeration.OperationType;
import com.competition.project.service.HistoryService;
import com.competition.project.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/project/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;


    @ApiOperation("查询操作日志")
    @RequestMapping("queryAllHistory")
    public Result queryAllHistory(){
        List<History> list = historyService.queryAllHistory();
        List<HistoryDTO> dtoArrayList = new ArrayList<>();
        for (History history : list) {
            dtoArrayList.add(new HistoryDTO(history));
        }
        return Result.ok().data("result",dtoArrayList);
    }

}

