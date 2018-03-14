package com.supervise.schedule;

import com.supervise.dao.mysql.entity.ScheduleStatusEntity;
import com.supervise.mail.MailService;
import com.supervise.message.MessageSender;
import com.supervise.schedule.job.DataLoadedSchedule;
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
    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSender messageSender;

    @Override
    public void schedule() {
        String scheduleName = scheduleName();
        String dupKey = scheduleName + (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()));
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
            e.printStackTrace();
        }
    }

     /**
     * 有发送失败的，发送信息通知
     *
     * @param dupKey
     */
    protected  void sendDataFailProcessor(String dupKey,String schName){
        //构造关键数据信息
        String dateType = schName;
        String batchDate ="";
        batchDate = dupKey.substring(schName.length(), dupKey.length()-3);
        //发送邮件通知
        this.mailService.sendEmailData(dateType,batchDate);
        //发送短信通知
        this.messageSender.sendSMSData(dateType,batchDate);
    }
    public abstract String scheduleName();

    public abstract void doSchedule(String dupKey)throws Exception;
}
