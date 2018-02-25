package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.viewDao.ViewRepaymentDao;
import com.supervise.schedule.AbstractSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private ViewRepaymentDao  repaymentDao;
	
    private final Logger logger = LoggerFactory.getLogger(DataLoadedSchedule.class);
    
    @Override
    public String scheduleName() {
        return  Constants.SCH_DATA_LOADED_SCHEDULE;
    }

    @Override
    public void doSchedule(String dupKey) {
        //doing
    	List<RepaymentEntity> repaymentLists =  new ArrayList();
    	List bankcreditLists =  new ArrayList();
    	List businessDateLists =  new ArrayList();
        //new DataConfig
        //数据处理
    	//1、
    	//从dupkey中获取当前批次
    	int lenth = Constants.SCH_DATA_LOADED_SCHEDULE.length();
    	String batchDate = dupKey.substring(lenth,dupKey.length()).substring(0,10);
    	//2、从VIEW中LOAD当期那天批次的数据，依次获取3张表
    	repaymentLists = this.repaymentDao.queryRepaymentFromView(batchDate);
    	//2、将LOAD的数据持久化到中间表
    	//3、打印本次LOAD的操作结果：成功或失败，结束
        logger.info("-------------------------data loaded job doing------------------");
    }
}
