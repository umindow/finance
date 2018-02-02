package com.supervise.schedule;

import com.supervise.config.role.DataType;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xishui.hb on 2018/2/2 上午10:20.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public enum QuartzScheduleManager {
    MANAGER;
    public final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private final Logger logger = LoggerFactory.getLogger(QuartzScheduleManager.class);

    QuartzScheduleManager() {

    }

    /**
     * 添加一个job,并启动job
     *
     * @param jobName
     * @param jobGroupName
     * @param jobTriggerName
     * @param executeCls
     * @param cronTime
     */
    public void addJob(String jobName, String jobGroupName, String jobTriggerName, Class<? extends Job> executeCls, String cronTime, DataType dataType) {
        try {
            logger.info("add Job,{},{},{},{},{}", jobName, jobGroupName, jobTriggerName, cronTime, dataType.getDataName());
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(executeCls).withIdentity(jobName, jobGroupName)
                    .build();
            jobDetail.getJobDataMap().put("dataType", dataType.getSchedule());
            CronTrigger cronTrigger = new CronTriggerImpl(jobName, jobTriggerName, cronTime);
            scheduler.scheduleJob(jobDetail, cronTrigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            logger.error("add Job Fail,JobName:" + jobName);
            throw new RuntimeException("添加定时任务错误,可能是CRON错误:" + cronTime);
        }
    }

    /**
     * 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param jobTriggerName
     */
    public void removeJob(String jobName, String jobGroupName, String jobTriggerName) {
        try {
            logger.info("Remove Job,{},{},{}", jobName, jobGroupName, jobTriggerName);
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (scheduler.checkExists(TriggerKey.triggerKey(jobName, jobTriggerName))) {
                scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobTriggerName));
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobTriggerName));
            }
            if (scheduler.checkExists(JobKey.jobKey(jobName, jobGroupName))) {
                scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException("Quartz Remove Job Exception.", e);
        }
    }
}
