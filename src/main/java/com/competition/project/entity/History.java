package com.competition.project.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author conchino
 * @since 2021-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="History对象", description="")
public class History implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "时间戳唯一序号")
    private String historyId;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作地址")
    private String operatorAddress;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作方法")
    private String operationMethod;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date operationTime;


    public History(String historyId, String operator,String operatorAddress, String operationType, String operationMethod,String parameter,String description){
        this.historyId = historyId;
        this.operator = operator;
        this.operatorAddress = operatorAddress;
        this.operationType = operationType;
        this.operationMethod = operationMethod;
        this.parameter = parameter;
        this.description = description;
    }

    public History(Long historyId, String operator,String operatorAddress, String operationType, String operationMethod,String parameter,String description){
        this.historyId = String.valueOf(historyId);
        this.operator = operator;
        this.operatorAddress = operatorAddress;
        this.operationType = operationType;
        this.operationMethod = operationMethod;
        this.parameter = parameter;
        this.description = description;
    }

}
