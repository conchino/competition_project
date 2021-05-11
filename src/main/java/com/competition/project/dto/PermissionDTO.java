package com.competition.project.dto;

import java.io.Serializable;

/* 权限数据传输类 */
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer permId;
    private String permName;

    public PermissionDTO(){
    }

    public PermissionDTO(Integer permId, String permName){
        this.permId = permId;
        this.permName = permName;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public Integer getPermId() {
        return permId;
    }

    public String getPermName() {
        return permName;
    }

    @Override
    public String toString() {
        return "PermissionVO{" +
                "permId=" + permId +
                ", permName='" + permName + '\'' +
                '}';
    }
}
