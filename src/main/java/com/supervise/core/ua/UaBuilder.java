package com.supervise.core.ua;

import com.supervise.dao.mysql.entity.UserActionEntity;

import java.util.Date;

/**
 * Created by xishui.hb on 2018/1/30 下午6:06.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class UaBuilder {
    private UserActionEntity userActionEntity;

    public UaBuilder() {
        this.userActionEntity = new UserActionEntity();
    }

    public static UaBuilder newBuilder() {
        return new UaBuilder();
    }

    public UaBuilder userId(long userId) {
        this.userActionEntity.setUserId(userId);
        return this;
    }

    public UaBuilder userName(String userName) {
        this.userActionEntity.setUserName(userName);
        return this;
    }

    public UaBuilder uaType(UaType uaType) {
        this.userActionEntity.setActionType(uaType.getUa());
        return this;
    }

    public UaBuilder uaContent(String content) {
        this.userActionEntity.setActionContent(content);
        return this;
    }
    public UserActionEntity builder() {
        this.userActionEntity.setActionTime(new Date());
        return userActionEntity;
    }
}
