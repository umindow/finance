package com.supervise.controller.vo;

import com.supervise.config.role.DataType;
import lombok.Data;

import java.util.List;

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
public class DataVo{
    private List<FieldValue> values;
    private Long dataId;
}
