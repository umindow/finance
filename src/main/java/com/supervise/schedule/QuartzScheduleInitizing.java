package com.supervise.schedule;

import com.supervise.config.role.DataType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by xishui.hb on 2018/2/2 上午10:26.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class QuartzScheduleInitizing {
    @PostConstruct
    public void initDbSchedule() {
        //加载数据库中已经有的任务列表
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobDataType(DataType.SUPERVISE_BIZ_DATA.getDataName());
        jobInfo.setJobName("test-job-name");
        jobInfo.setJobGroup("test-job-group");
        jobInfo.setJobTriggerGroup("test-job-trigger-group");
        jobInfo.setCron("0 0/1 * * * ?");
        QuartzScheduleManager.MANAGER.addJob(jobInfo.getJobName(), jobInfo.getJobGroup(), jobInfo.getJobTriggerGroup(), QuartzScheduleExecutor.class, jobInfo.getCron(), DataType.typeOfName(jobInfo.getJobDataType()));
    }
}
