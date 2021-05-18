package com.competition;

import com.competition.project.dto.WorkRecordsDTO;
import com.competition.project.service.LabelClassService;
import com.competition.project.service.WorkRecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class WorkRecordsTest {
    @Autowired
    @Lazy
    private WorkRecordsService workRecordsService;
    @Autowired
    @Lazy
    private LabelClassService labelClassService;

    @Test
    public void getWorkRecords(){
        List<WorkRecordsDTO> recordsDTOList = workRecordsService.getWorkRecords("145236");
        for (WorkRecordsDTO workRecordsDTO : recordsDTOList) {
            System.out.println(workRecordsDTO);
        }
    }

    @Test
    public void getEmployeesRecordsData(){
        List<WorkRecordsDTO> recordsDTOList = workRecordsService.getWorkRecords("145236");
        List<String> labelList = labelClassService.getAllLabelClass();
        List<List<Object>> source = new ArrayList<>();
        Map<String, Object> hashMap = new HashMap<>();

        for (String str : labelList) {
            hashMap.put(str, 0);
            List<Object> strings = new ArrayList<>();
            strings.add(str);
            source.add(strings);
        }

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("product");

        for (int i = 0; i < recordsDTOList.size(); i+=7) {
            Date time = recordsDTOList.get(i).getCreateTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String currentTime = format.format(time);
            arrayList.add(currentTime);

            for (int j = i; j < i + 7; j++) {
                WorkRecordsDTO recordsDTO = recordsDTOList.get(j);
                System.out.println(recordsDTO);
                String labelName = labelClassService.getLabelNameById(recordsDTO.getLabelClass());

                if (hashMap.containsKey(labelName)) {
                    hashMap.put(labelName, (int) hashMap.get(labelName) + 1);
                    System.out.println(hashMap);
                }
            }

            for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                for (List<Object> objectList : source) {
                    if (objectList.contains(key)) {
                        objectList.add(value);
                    }
                }
            }
        }

        source.add(arrayList);

        System.out.println(source);
    }
}
