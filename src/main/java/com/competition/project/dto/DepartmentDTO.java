package com.competition.project.dto;

import com.competition.project.entity.Department;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String departId;
    private String departName;
    private String belongs;

    public DepartmentDTO(Department department){
        this.departId = department.getDepartId();
        this.departName = department.getDepartName();
        this.belongs = department.getBelongs();
    }
}
