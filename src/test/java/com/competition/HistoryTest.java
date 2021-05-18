package com.competition;

import com.competition.project.dto.HistoryDTO;
import com.competition.project.entity.History;
import com.competition.project.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
public class HistoryTest {
    @Autowired
    HistoryService historyService;

    @Test
    public void addHistoryRecords(){
        Boolean flag = historyService.addHistoryRecords(new History(new Date().getTime(), "操作人员","操作地址", "操作类型","操作方法","参数","操作描述"));
        if (flag) {
            System.out.println("插入成功");
        }
    }

    @Test
    public void queryHistory(){
        ArrayList<HistoryDTO> dtoArrayList = new ArrayList<>();
        for (History history : historyService.queryAllHistory()) {
            dtoArrayList.add(new HistoryDTO(history));
        }

        for (HistoryDTO dto : dtoArrayList) {
            System.out.println(dto);
        }
    }


}
