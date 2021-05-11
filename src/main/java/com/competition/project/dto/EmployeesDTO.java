package com.competition.project.dto;

import com.competition.project.entity.Employees;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeesDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private String workId;
    private String name;
    private String post;
    private String depart;
    private String company;
    private String phone;
    private String email;

    public EmployeesDTO(Employees employees){
        this.name = employees.getName();
        this.post = employees.getPost();
        this.depart = employees.getDepart();
        this.company = employees.getCompany();
        this.phone = employees.getPhone();
        this.email = employees.getEmail();
    }
}
