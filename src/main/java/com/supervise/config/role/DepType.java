package com.supervise.config.role;

/**
 * Created by xishui.hb on 2018/2/6 上午9:45.
 * 有哪些部门，会和字段绑定
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum DepType {
    ALL(0, "所有部门"), FINANCE_DEP(1, "财务部"),
    COMPREHENSIVE_DEP(2, "综合管理部"),
    RISK_DEP(3, "风险管理部"),
    LAW_DEP(4, "资产管理以及法律事务部");

    private int depId;
    private String depDesc;

    DepType(int depId, String depDesc) {
        this.depId = depId;
        this.depDesc = depDesc;
    }
}
