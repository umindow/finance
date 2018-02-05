package com.supervise.controller.vo;

import com.supervise.schedule.JobInfo;
import lombok.Data;

/**
 * 目前只有每一个的定时任务的配置，没其他配置，所以不靠谱做泛型匹配
 * Created by ${admin} on 2018/2/3.
 */
@Data
public class ConfigVo{
    private long id;
    private int configType;
    private String configTypeDesc;
    private JobInfo jobInfo;
}
