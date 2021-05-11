package com.competition.project.annotation;

import com.competition.project.enumeration.OperationType;

import java.lang.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/* 自定义日志功能注解 @Log */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Log{

    public String value() default "";
    /* 执行的操作类型 */
    public OperationType operationType() default OperationType.DEFAULT;
    /* 执行的具体操作 */
    public String operationName() default "";
    /* 方法描述 */
    public String description() default "";
}
