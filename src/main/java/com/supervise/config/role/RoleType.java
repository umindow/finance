package com.supervise.config.role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by xishui.hb on 2018/1/30 下午2:04.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum RoleType {
    NOMAL("普通用户", 10), MANAGER("管理员", 20), SUPER_MANAGER("超级管理员", 100);
    @Getter @Setter
    private String roleName;//角色描述
    @Getter @Setter
    private int roleLevel;//角色级别，跟user的level对应

    RoleType(String roleName, int roleLevel) {
        this.roleName = roleName;
        this.roleLevel = roleLevel;
    }
}
