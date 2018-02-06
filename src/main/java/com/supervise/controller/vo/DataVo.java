package com.supervise.controller.vo;

import com.supervise.config.role.DataType;
import lombok.Data;

/**
 * Created by xishui.hb on 2018/2/6 上午10:36.
 * 统一的数据对象
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public abstract class DataVo{
    private DataType dataType;//是什么数据类型
    private boolean modify;//是否可以修改
    private String depDesc;//字段所属部门
}
