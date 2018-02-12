package com.supervise.config.role;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    FINANCE_DEP(1, "财务部"),
    COMPREHENSIVE_DEP(2, "综合管理部"),
    RISK_DEP(3, "风险管理部"),
    LAW_DEP(4, "资产管理以及法律事务部");
    @Getter @Setter
    private int depId;
    @Getter @Setter
    private String depDesc;

    DepType(int depId, String depDesc) {
        this.depId = depId;
        this.depDesc = depDesc;
    }

    public static List<Integer>  listDepIds(){
        List<Integer> depIds = Lists.newArrayList();
        for(final DepType depType:values()){
            depIds.add(depType.depId);
        }
        return depIds;
    }
}
