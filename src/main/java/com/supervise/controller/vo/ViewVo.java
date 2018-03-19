package com.supervise.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xishui.hb on 2018/3/4 下午8:03.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class ViewVo implements Serializable{
    private String moduleName;
    private int dataType;//数据类型
    private String modifyUrl;//修改url
    private String deleteUrl;//
    //银行授信
    private String creditStartDate;//格式 "yyyy-MM-dd"
    private String creditEndDate;//格式 "yyyy-MM-dd"
    //其他
    private String projId;//
    private String clientName;
    private String batchDate;//格式 "yyyy-MM-dd"

    private boolean creditType;
}
