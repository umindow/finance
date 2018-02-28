package com.supervise.schedule;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.supervise.common.DateUtils;
import com.supervise.webservice.JgProjectServiceImpl;
import com.supervise.webservice.Webservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/28 上午11:06.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class AbstractSenderSchedule<T> extends AbstractSchedule {
    private final Logger logger = LoggerFactory.getLogger(AbstractSenderSchedule.class);
    private final static int RETRY_TIME = 3;

    @Override
    public void doSchedule(String dupKey) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        T data = loadSenderData(DateUtils.getSimpleDateByAdded(date, "00:00:00"), DateUtils.getSimpleDateByAdded(date, "23:59:59"));
        if (null == data) {
            logger.error("Load SenderSchedule Data Return is null.dupkey is:" + dupKey);
            return;
        }
        if (!checkData(data)) {
            logger.error("Checker SenderSchedule Data Return false.dupkey is:" + dupKey);
            return;
        }

        int retry = 1;
        while (retry <= RETRY_TIME) {
            try {
                if (senderData(data)) {
                    return;
                }
            } catch (Exception e) {
                logger.error("Send Data Exception:%s,dupkey:%", e, dupKey);
            }
            retry++;
        }
        sendDataFailProcessor(dupKey);
    }

    public abstract T loadSenderData(Date fromDate, Date toDate);

    public abstract boolean checkData(T t);

    public abstract boolean senderData(T t) throws Exception;

    protected Webservice webService() {
        Service service = new JgProjectServiceImpl();
        return service.getPort(Webservice.class);
    }

    protected XMLGregorianCalendar xmlGregorianCalendar(Date date) {
        XMLGregorianCalendar xmlGregorianCalendar = new XMLGregorianCalendarImpl();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        xmlGregorianCalendar.setYear(calendar.get(Calendar.YEAR));
        xmlGregorianCalendar.setMonth(calendar.get(Calendar.MONTH));
        xmlGregorianCalendar.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        xmlGregorianCalendar.setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
        return xmlGregorianCalendar;
    }

    private void sendDataFailProcessor(String dupKey) {
        String scheduleName = scheduleName();
    }
}
