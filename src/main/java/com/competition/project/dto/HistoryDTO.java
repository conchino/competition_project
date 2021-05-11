package com.competition.project.dto;

import com.competition.project.entity.History;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String historyId;
    private String operator;
    private String operatorAddress;
    private String operationType;
    private String operationMethod;
    private String parameter;
    private String description;
    private Date operationTime;

    public HistoryDTO(History history){
        this.historyId = history.getHistoryId();
        this.operator = history.getOperator();
        this.operatorAddress = history.getOperatorAddress();
        this.operationType = history.getOperationType();
        this.operationMethod = history.getOperationMethod();
        this.parameter = history.getParameter();
        this.description = history.getDescription();
        this.operationTime = history.getOperationTime();
    }

}
