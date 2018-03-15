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
    //系统业务、退费、还款
    FINANCE_DEP(1, "财务部",new DataType[]{DataType.SUPERVISE_BIZ_DATA,DataType.SUPERVISE_FEE_DATA,DataType.SUPERVISE_REBACK_DATA}),
    //银行授信、系统业务
    COMPREHENSIVE_DEP(2, "综合营运部",new DataType[]{DataType.SUPERVISE_BANK_DATA,DataType.SUPERVISE_BIZ_DATA}),
    //系统业务、退费、代偿
    RISK_DEP(3, "风险管理部",new DataType[]{DataType.SUPERVISE_BIZ_DATA,DataType.SUPERVISE_REPLACE_DATA}),
    //追偿
    LAW_DEP(4, "资产法务部",new DataType[]{DataType.SUPERVISE_TRACE_DATA});
    @Getter @Setter
    private int depId;
    @Getter @Setter
    private String depDesc;
    @Getter @Setter
    private DataType[] dataTypes;

    DepType(int depId, String depDesc,DataType[] dataTypes) {
        this.depId = depId;
        this.depDesc = depDesc;
        this.dataTypes = dataTypes;
    }

    public static List<Integer>  listDepIds(){
        List<Integer> depIds = Lists.newArrayList();
        for(final DepType depType:values()){
            depIds.add(depType.depId);
        }
        return depIds;
    }

    public static DepType depType(int depId){
        for(final DepType depType:values()){
            if(depType.getDepId() == depId){
                return depType;
            }
        }
        return null;
    }
    public static String depDesc(int depId){
        for(final DepType depType:values()){
           if(depType.getDepId() == depId){
               return depType.getDepDesc();
           }
        }
        return "未知";
    }
}
