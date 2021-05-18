package com.competition.project.dto;

import com.competition.project.entity.WorkRecords;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class WorkRecordsDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private String workId;
    private String workDescription;
    private String labelClass;
    private Date createTime;

    public WorkRecordsDTO(){
    }

    public WorkRecordsDTO(WorkRecords workRecords){
        this.workId = workRecords.getWorkId();
        this.workDescription = workRecords.getWorkDescription();
        this.labelClass = workRecords.getLabelClass();
        this.createTime = workRecords.getCreateTime();
    }

}
