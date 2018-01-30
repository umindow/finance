package com.supervise.common;

import com.supervise.dao.mysql.entity.UserEntity;

/**
 * Created by xishui.hb on 2018/1/30 下午3:17.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum  SessionUser {
    INSTANCE;
    private final ThreadLocal<UserEntity> threadLocal = new ThreadLocal<UserEntity>();

    public void setCurrentUser(UserEntity userEntity){
        threadLocal.set(userEntity);
    }
    public UserEntity getCurrentUser(){
        return threadLocal.get();
    }
    public void removeSession() {
        threadLocal.remove();
    }
}
