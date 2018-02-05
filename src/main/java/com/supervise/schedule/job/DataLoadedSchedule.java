package com.supervise.schedule.job;

import com.supervise.schedule.AbstractSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xishui.hb on 2018/1/31 上午10:59.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class DataLoadedSchedule extends AbstractSchedule {
    private final Logger logger = LoggerFactory.getLogger(DataLoadedSchedule.class);
    @Override
    public String scheduleName() {
        return "data-loaded-schedule";
    }

    @Override
    public void doSchedule(String dupKey) {
        //doing
        //new DataConfig
        //数据处理
        logger.info("-------------------------data loaded job doing------------------");
    }
}
