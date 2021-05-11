package com.competition.project.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * 设置填充策略
 */
@Slf4j
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) { // 在mybatis-plus执行插入时，为指定字段填充指定值
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("operationTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {// 在mybatis-plus更新操作时，为指定字段填充指定值
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
