package com.competition.project.enumeration;

/* 操作类型枚举类 */
public enum OperationType {

    DEFAULT(0, "default", "默认值"),
    SELECT(1,"select","查找"),
    INSERT(2, "insert", "增加"),
    UPDATE(3, "update", "修改"),
    DELETE(3, "delete", "删除");

    private final int id;
    private final String type;
    private final String description;

    OperationType(int id,String type, String description){
        this.id  = id;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getType(){
        return type;
    }

    public String getDescription() {
        return description;
    }


}
