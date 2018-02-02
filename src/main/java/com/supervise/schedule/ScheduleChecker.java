package com.supervise.schedule;

import com.supervise.dao.mysql.entity.ScheduleStatusEntity;
import com.supervise.dao.mysql.mapper.ScheduleStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * Created by xishui.hb on 2018/1/31 上午11:04.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class ScheduleChecker implements Checker {
    private final Logger logger = LoggerFactory.getLogger(ScheduleChecker.class);
    @Autowired
    private ScheduleStatusMapper scheduleStatusMapper;

    @Override
    public boolean checker(String dupKey, String scheduleName) {
        //插入任务并初始化状态和时间
        try {
            ScheduleStatusEntity scheduleStatusEntity = new ScheduleStatusEntity();
            scheduleStatusEntity.setCreateTime(new Date());
            scheduleStatusEntity.setDupkey(dupKey);
            scheduleStatusEntity.setScheduleName(scheduleName);
            scheduleStatusEntity.setUpdateTime(new Date());
            scheduleStatusEntity.setScheduleStatus(ScheduleStatusEntity.ScheduleStatus.INIT.getStatus());
            scheduleStatusMapper.insert(scheduleStatusEntity);
            return true;
        } catch (Exception e) {
            logger.info("Schedule checker,is not this machine processor.");
        }
        return false;
    }

    public void changeScheduleStatus(String dupKey, ScheduleStatusEntity.ScheduleStatus scheduleStatus) {
        try {
            scheduleStatusMapper.updateStatus(scheduleStatus.getStatus(),dupKey,new Date());
        } catch (Exception e) {
            logger.error("Update Schedule Status Fail.", e);
        }
    }
}
