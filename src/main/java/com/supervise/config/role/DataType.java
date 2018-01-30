package com.supervise.config.role;

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
    SUPERVISE_BIZ_DATA("系统业务数据", 100),
    SUPERVISE_BANK_DATA("银行授信数据", 200),
    SUPERVISE_REBACK_DATA("还款数据", 300),
    SUPERVISE_FEE_DATA("收费退费数据", 400),
    SUPERVISE_REPLACE_DATA("代偿数据", 500),
    SUPERVISE_TRACE_DATA("追偿数据", 600);
    @Getter@Setter
    private String dataName;
    @Getter@Setter
    private int dataLevel;

    DataType(String dataName, int dataLevel) {
        this.dataName = dataName;
        this.dataLevel = dataLevel;
    }
}
