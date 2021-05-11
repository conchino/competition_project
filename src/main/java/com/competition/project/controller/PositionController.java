package com.competition.project.controller;


import com.competition.project.dto.PositionDTO;
import com.competition.project.entity.Position;
import com.competition.project.service.PositionService;
import com.competition.project.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/project/position")
public class PositionController {
    @Autowired
    private PositionService positionService;
    /* 根据部门获取职位 */

    @RequestMapping(value = "/queryPostByDepart/{depart}",method = RequestMethod.GET)
    public Result queryPostByDepart(@PathVariable("depart") String depart){
        List<Position> positionList = positionService.queryPostByDepart(depart);
        List<PositionDTO> dtoArrayList = new ArrayList<>();
        for (Position position : positionList) {
            dtoArrayList.add(new PositionDTO(position));
        }
        return Result.ok().data("positionList",dtoArrayList);
    }

    @RequestMapping("getAllPost")
    public Result getAllPost(){
        return Result.ok().data("result", positionService.getAllPost());
    }
}

