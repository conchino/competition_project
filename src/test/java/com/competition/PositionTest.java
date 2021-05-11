package com.competition;

import com.competition.project.dto.PositionDTO;
import com.competition.project.entity.Position;
import com.competition.project.service.PositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PositionTest {
    @Autowired
    private PositionService positionService;

    @Test
    public void queryPostByDepart(){
        List<Position> positionList = positionService.queryPostByDepart("101");
        List<PositionDTO> dtoArrayList = new ArrayList<>();
        for (Position position : positionList) {
            dtoArrayList.add(new PositionDTO(position));
        }
        System.out.println(dtoArrayList);
    }
}
