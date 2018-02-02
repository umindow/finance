package com.supervise.config.role;

import com.supervise.schedule.job.DataLoadedSchedule;
import lombok.Getter;
import lombok.Setter;

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
    SUPERVISE_BIZ_DATA("系统业务数据", 100, 1, DataLoadedSchedule.class.getSimpleName()),
    SUPERVISE_BANK_DATA("银行授信数据", 200, 1, null),
    SUPERVISE_REBACK_DATA("还款数据", 300, 1, null),
    SUPERVISE_FEE_DATA("收费退费数据", 400, 1, null),
    SUPERVISE_REPLACE_DATA("代偿数据", 500, 1, null),
    SUPERVISE_TRACE_DATA("追偿数据", 600, 1, null);
    @Getter
    @Setter
    private String dataName;
    @Getter
    @Setter
    private int dataLevel;
    @Getter
    @Setter
    private int dataType;//是否是定时任务,1：是，0,不是
    @Setter
    @Getter
    private String schedule;

    DataType(String dataName, int dataLevel, int dataType, String schedule) {
        this.dataName = dataName;
        this.dataLevel = dataLevel;
        this.dataType = dataType;
        this.schedule = schedule;
    }

    public static DataType typeOfName(String dataName){
        for(final DataType dataType : values()){
            if(dataType.getDataName().equals(dataName)){
                return dataType;
            }
        }
        return null;
    }
}
