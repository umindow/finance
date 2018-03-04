package com.supervise.controller.vo;

import lombok.Data;

/**
 * Created by xishui.hb on 2018/3/4 下午4:20.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class FieldValue {
    private Object value;
    private String fieldName;
    private String filedCnName;
    private boolean modify;

    public FieldValue(Object value, String fieldName, String filedCnName, boolean modify) {
        this.value = value;
        this.fieldName = fieldName;
        this.filedCnName = filedCnName;
        this.modify = modify;
    }
}
