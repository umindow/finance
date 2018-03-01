package com.supervise.schedule;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.supervise.common.Constants;
import com.supervise.common.Spliter;
import com.supervise.webservice.JgProjectServiceImpl;
import com.supervise.webservice.Webservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        List<T> data = loadSenderData(date);
        if (null == data) {
            logger.error("Load SenderSchedule Data Return is null.dupkey is:" + dupKey);
            return;
        }
        if (checkData(data)) {
            logger.error("Checker SenderSchedule Data Return false.dupkey is:" + dupKey);
            return;
        }
        List<List<T>> datas = new DataSpliter<T>().split(data, Constants.BACTCH_SEND_MAX_NUM);
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        int errorCount = 0;
        for (final List<T> t : datas) {
            if (CollectionUtils.isEmpty(t)) {
                continue;
            }
            int retry = 1;
            while (retry <= RETRY_TIME) {
                try {
                    if (senderData(t)) {
                        retry += RETRY_TIME;
                        continue;
                    }
                } catch (Exception e) {
                    logger.error("Send Data Exception:%s,dupkey:%", e, dupKey);
                }
                errorCount++;
            }
        }
        if (errorCount > 0) {
            sendDataFailProcessor(dupKey,scheduleName());
        }
    }

    public abstract List<T> loadSenderData(String batchDate);

    public abstract boolean checkData(List<T> t);

    public abstract boolean senderData(List<T> t) throws Exception;

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




    public class DataSpliter<T> implements Spliter<T> {
        @Override
        public List<List<T>> split(List<T> data, int splitSize) {
            List<List<T>> resDatas = new ArrayList<List<T>>();
            if (CollectionUtils.isEmpty(data) || splitSize >= data.size()) {
                resDatas.add(data);
            } else {
                //未完，待续
            }
            return resDatas;
        }
    }
}
