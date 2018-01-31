package com.supervise.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xishui.hb on 2018/1/31 上午11:07.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class AbstractSchedule implements Schedule {
    private final Logger logger = LoggerFactory.getLogger(DataLoadedSchedule.class);
    @Autowired
    private ScheduleChecker scheduleChecker;

    @Override
    public void schedule() {
        String scheduleName = scheduleName();
        String dupKey = scheduleName + "_" + "date/到天";
        if (!scheduleChecker.checker(dupKey, scheduleName)) {
            //不是本台机器跑这个任务
            logger.info("This schedule is not mine machine do. dupKey:" + dupKey);
            return;
        }
        doSchedule(dupKey);
    }

    public abstract String scheduleName();

    public abstract void doSchedule(String dupKey);
}
