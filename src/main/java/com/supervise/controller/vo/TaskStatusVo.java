package com.supervise.controller.vo;

import lombok.Data;

/**
 *
 * Created by ${admin} on 2018/2/3.
 */
@Data
public class TaskStatusVo {
    private String dataName;
    private String optionType;
    private String result;
    private String optime;//格式 "yyyy-MM-dd HH:mm:ss"
    private String dataNum;

}
