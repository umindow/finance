package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.core.data.loadProces.BankCreditLoader;
import com.supervise.core.data.loadProces.BusinessDataLoader;
import com.supervise.core.data.loadProces.RepaymentLoader;
import com.supervise.schedule.AbstractSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    private RepaymentLoader repaymentLoader;

    @Autowired
    private BusinessDataLoader businessDataLoader;

    @Autowired
    private BankCreditLoader bankCreditLoader;

    private final Logger logger = LoggerFactory.getLogger(DataLoadedSchedule.class);
    
    @Override
    public String scheduleName() {
        return  Constants.SCH_DATA_LOADED_SCHEDULE;
    }

    @Override
    public void doSchedule(String dupKey) throws  Exception{
        //数据处理
    	//1、
    	//从dupkey中获取当前批次作为查询条件
//    	int lenth = Constants.SCH_DATA_LOADED_SCHEDULE.length();
//    	String batchDate = dupKey.substring(lenth,dupKey.length()).substring(0,10);
//        Date date = DateUtils.String2Date(batchDate,Constants.YYYY_MM_DD, Locale.ENGLISH);
        Date nowDate = new Date();
        Date preDate = DateUtils.previousDay(nowDate);
        String batchDate = new SimpleDateFormat(Constants.YYYY_MM_DD).format(nowDate);

        logger.info("data Loader job Start,batchDate is :"+batchDate);
        try {
            //2、从VIEW中LOAD businessData当期那天批次的数据，并将LOAD的数据持久化到中间表
            logger.info("-------------------------data businessDataLoader job doing------------------");
            this.businessDataLoader.handler(batchDate);
            logger.info("-------------------------data businessDataLoader job done------------------");
        }catch(Exception e){
            logger.error("data businessDataLoader job ERROR,batchDate is :"+batchDate);
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();


        }
        try {
            //3、从VIEW中LOAD repayment当期那天批次的数据，并将LOAD的数据持久化到中间表
            logger.info("-------------------------data repaymentLoader job doing------------------");
            this.repaymentLoader.handler(batchDate);
            logger.info("-------------------------data repaymentLoader job done------------------");
        }catch(Exception e){
            logger.error("data repaymentLoader job ERROR,batchDate is :"+batchDate);
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw  new Exception(e);
        }
        try {
            //4、从VIEW中LOAD bankCredit当期那天批次的数据，并将LOAD的数据持久化到中间表
            logger.info("-------------------------data bankCreditLoader job doing------------------");
            this.bankCreditLoader.handler(batchDate);
            logger.info("-------------------------data bankCreditLoader job done------------------");
        }catch(Exception e){
            logger.error("data bankCreditLoader job ERROR,batchDate is :"+batchDate);
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
            throw  new Exception(e);
        }
    	//3、打印本次LOAD的操作结果：成功或失败，结束
        logger.info("All data Loader job Success,batchDate is :"+batchDate);
    }
}
