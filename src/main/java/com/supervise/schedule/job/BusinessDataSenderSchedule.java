package com.supervise.schedule.job;

import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuProjectInfo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/2/28 下午3:37.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class BusinessDataSenderSchedule extends AbstractSenderSchedule<List<JgBuProjectInfo>>{
    @Override
    public List<JgBuProjectInfo> loadSenderData(Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public boolean checkData(List<JgBuProjectInfo> jgBuProjectInfos) {
        return false;
    }

    @Override
    public boolean senderData(List<JgBuProjectInfo> jgBuProjectInfos) throws Exception {
        return false;
    }

    @Override
    public String scheduleName() {
        return "";
    }
}
