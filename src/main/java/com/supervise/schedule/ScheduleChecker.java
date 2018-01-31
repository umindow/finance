package com.supervise.schedule;

import org.springframework.stereotype.Component;

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
public class ScheduleChecker implements Checker{
    @Override
    public boolean checker(String dupKey, String scheduleName) {
        //插入任务并初始化状态和时间
        return false;
    }
}
