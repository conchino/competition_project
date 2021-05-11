package com.competition.project.dto;

import com.competition.project.entity.Subsidiary;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubsidiaryDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private String subId;
    private String subName;

    public SubsidiaryDTO(Subsidiary subsidiary){
        this.subId = subsidiary.getSubId();
        this.subName = subsidiary.getSubName();
    }
}
