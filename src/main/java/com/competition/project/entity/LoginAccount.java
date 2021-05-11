package com.competition.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginAccount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号(唯一)
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 随机加密盐
     */
    private String salt;

    /**
     * 账号权限(1/2/3)
     */
    private Integer perm;

    /**
     * 使用者工号
     */
    private String userWorkId;

    /**
     * 账号是否可用
     */
    private Integer feasible;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public LoginAccount(Date updateTime){
        this.updateTime = updateTime;
    }

    public LoginAccount(String account, String password, String salt, Integer perm, String userWorkId, Integer feasible) {
        this.account = account;
        this.password = password;
        this.salt = salt;
        this.perm = perm;
        this.userWorkId = userWorkId;
        this.feasible = feasible;
    }
}
