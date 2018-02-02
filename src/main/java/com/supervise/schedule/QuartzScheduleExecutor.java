package com.supervise.schedule;

import com.alibaba.fastjson.JSON;
import com.supervise.support.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xishui.hb on 2018/2/2 上午11:05.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class QuartzScheduleExecutor implements Job {
    private final Logger logger = LoggerFactory.getLogger(QuartzScheduleExecutor.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("QuartzScheduleExecutor started." + JSON.toJSONString(jobExecutionContext.getJobDetail()));
        String jobExecuteCls = jobExecutionContext.getJobDetail().getJobDataMap().getString("dataType");
        Schedule schedule = SpringContextHolder.getBean(toLowerCaseFirstOne(jobExecuteCls));
        schedule.schedule();
        logger.info("QuartzScheduleExecutor completed." + JSON.toJSONString(jobExecutionContext.getJobDetail()));
    }

    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
