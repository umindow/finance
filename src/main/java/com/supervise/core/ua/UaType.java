package com.supervise.core.ua;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by xishui.hb on 2018/1/30 下午6:11.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum UaType {
    USER_ADD("用户新增"),
    USER_DELETE("用户删除"),
    USER_MODIFY("用户修改"),
    DATA_QUERY("数据查询"),
    DATA_MODIFY("数据修改"),
    DATA_SENDER("数据发送"),
    DATA_DELETE("数据删除"),
    LOG_QUERY("日志查询");
    @Getter @Setter
    private String ua;

    UaType(String ua) {
        this.ua = ua;
    }
}
