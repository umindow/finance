package com.supervise.config.role;

import com.supervise.schedule.job.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午2:14.
 * 定义数据类型，在具体操作的时候，对数据类型做权限
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum DataType {
    SUPERVISE_BIZ_DATA("系统业务数据", 100, 1,1, BusinessDataSenderSchedule.class.getSimpleName()),
    SUPERVISE_BANK_DATA("银行授信数据", 200, 1,1, BankTrustSenderSchedule.class.getSimpleName()),
    SUPERVISE_REBACK_DATA("还款数据", 300, 1,1, RepaymentSenderSchedule.class.getSimpleName()),
    SUPERVISE_FEE_DATA("收费退费数据", 400, 1,1, FeeAndRefundSenderSchedule.class.getSimpleName()),
    SUPERVISE_REPLACE_DATA("代偿数据", 500, 1,1, CompensatorySenderSchedule.class.getSimpleName()),
    SUPERVISE_TRACE_DATA("追偿数据", 600, 1,1, RecourseSenderSchedule.class.getSimpleName()),
    SUPERVISE_LOAD_VIEWDATA("原始数据",700, 1,1, DataLoadedSchedule.class.getSimpleName());//从中间表LOAD数据的定时进程

    @Getter
    @Setter
    private String dataName;
    @Getter
    @Setter
    private int dataLevel;

    @Getter
    @Setter
    private int dataType;//是否是定时任务,1：是，0,不是
    @Getter@Setter
    private int ifHtml;
    @Setter
    @Getter
    private String schedule;

    DataType(String dataName, int dataLevel, int dataType,int ifHtml, String schedule) {
        this.dataName = dataName;
        this.dataLevel = dataLevel;
        this.dataType = dataType;
        this.schedule = schedule;
        this.ifHtml = ifHtml;
    }

    public static List<DataType> listHtmlDataType(){
        List<DataType> htmlDataTypes = new ArrayList<DataType>();
        for(final DataType dataType : values()){
            if(dataType.ifHtml == 1){
                htmlDataTypes.add(dataType);
            }
        }
        return htmlDataTypes;
    }
    public static DataType typeOfName(String dataName){
        for(final DataType dataType : values()){
            if(dataType.getDataName().equals(dataName)){
                return dataType;
            }
        }
        return null;
    }
    public static DataType typeOfType(int type){
        for(final DataType dataType : values()){
            if(dataType.getDataType() == type){
                return dataType;
            }
        }
        return null;
    }
}
