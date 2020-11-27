package com.tanhua.sso.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/*拦截器填充数据 拦截数据 并自动填充 至容器中   */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object created = getFieldValByName("created", metaObject);
        if(created==null){
            setFieldValByName("created", new Date(),metaObject );
        }
        Object updated = getFieldValByName("updated", metaObject);
        if(updated==null){
            setFieldValByName("updated", new Date(),metaObject );
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updated", new Date(), metaObject);

    }
}
