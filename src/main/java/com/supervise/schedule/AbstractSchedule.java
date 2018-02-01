package com.supervise.schedule;

import com.supervise.dao.mysql.entity.ScheduleStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String dupKey = scheduleName + (new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date()));
        try {
            if (!scheduleChecker.checker(dupKey, scheduleName)) {
                //不是本台机器跑这个任务
                logger.info("This schedule is not mine machine do. dupKey:" + dupKey);
                return;
            }
            doSchedule(dupKey);
            scheduleChecker.changeScheduleStatus(dupKey, ScheduleStatusEntity.ScheduleStatus.COMPLETE);
        } catch (Exception e) {
            scheduleChecker.changeScheduleStatus(dupKey, ScheduleStatusEntity.ScheduleStatus.FAILED);
        }
    }

    public abstract String scheduleName();

    public abstract void doSchedule(String dupKey);
}
