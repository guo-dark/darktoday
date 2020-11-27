package com.tanhua.sso.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.omg.CORBA.DATA_CONVERSION;

import java.util.Date;
/**
 *抽象类的使用 @TableField(.. , update="%s+1")(update="now()") 其中 %s 会填充为字段
 * @TableField(condition = SqlCondition.LIKE) 模糊查询 selectpage(new Page<int,int>,wrapper)
 * exist=false表示 字段中 不存在
 *与配置类一起 metaobejcthandler
 */

public abstract  class BasePojo {
    @TableField(fill= FieldFill.INSERT)
    private Date created;
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date updated;
}
