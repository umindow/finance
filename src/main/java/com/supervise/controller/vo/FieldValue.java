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
    private boolean date =false;
    private String dateFormat;
    private boolean time=false;
    private String timeFormat="hh:mm:ss";

    public FieldValue(Object value, String fieldName, String filedCnName, boolean modify) {
        this.value = value;
        this.fieldName = fieldName;
        this.filedCnName = filedCnName;
        this.modify = modify;
    }

    public FieldValue(Object value, String fieldName, String filedCnName, boolean modify, boolean date, String dateFormat) {
        this.value = value;
        this.fieldName = fieldName;
        this.filedCnName = filedCnName;
        this.modify = modify;
        this.date = date;
        this.dateFormat = dateFormat;
    }

    public FieldValue(Object value, String fieldName, String filedCnName, boolean modify, boolean date, String dateFormat, boolean time, String timeFormat) {
        this.value = value;
        this.fieldName = fieldName;
        this.filedCnName = filedCnName;
        this.modify = modify;
        this.date = date;
        this.dateFormat = dateFormat;
        this.time = time;
        this.timeFormat = timeFormat;
    }
}
