package com.supervise.schedule;

import lombok.Data;

/**
 * Created by xishui.hb on 2018/2/2 上午11:18.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class JobInfo {
    public static final String DEFAULT_JOB_GROUP = "default.job.group";
    public static final String DEFAULT_JOB_TRIGGER_GROUP = "default.job.trigger.group";
    private String jobName;
    private String cron;
    private String jobGroup;
    private String jobTriggerGroup;
    private String jobDataType;//对应DataType.dataName

    @Override
    public String toString() {
        return "JobInfo{" +
                "jobName='" + jobName + '\'' +
                ", cron='" + cron + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobTriggerGroup='" + jobTriggerGroup + '\'' +
                ", jobDataType='" + jobDataType + '\'' +
                '}';
    }
}
