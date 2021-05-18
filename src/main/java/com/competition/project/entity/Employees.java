package com.competition.project.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author conchino
 * @since 2021-03-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employees implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工号
     */
    private String workId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 工龄
     */
    private Integer workAge;

    /**
     * 职位
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String post;

    /**
     * 部门
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String depart;

    /**
     * 公司
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String company;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 住址
     */
    private String address;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Employees(String depart){
        this.depart = depart;
    }
    public Employees(String post, String depart, String company){
        this.post = post;
        this.depart = depart;
        this.company = company;
    }
}
