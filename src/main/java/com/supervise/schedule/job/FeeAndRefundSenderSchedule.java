package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuChargeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/2/28 下午4:04.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class FeeAndRefundSenderSchedule extends AbstractSenderSchedule<JgBuChargeRecord> {

    private final Logger logger = LoggerFactory.getLogger(FeeAndRefundSenderSchedule.class);
    @Autowired
    private FeeAndRefundDao feeAndRefundDao;

    @Override
    public List<JgBuChargeRecord> loadSenderData(String batchDate) {
//        Example example = new Example(FeeAndRefundEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);
        List<FeeAndRefundEntity> feeAndRefundEntitys = feeAndRefundDao.queryFeeAndRefundFormMiddleDB(batchDate);
        return CollectionUtils.isEmpty(feeAndRefundEntitys) ? null : new FeeAndRefundParserConvert().covert(feeAndRefundEntitys);
    }

    @Override
    public boolean checkData(List<JgBuChargeRecord> feeAndRefundEntitys) {

        return CollectionUtils.isEmpty(feeAndRefundEntitys);
    }

    @Override
    public boolean senderData(List<JgBuChargeRecord> feeAndRefundEntitys) throws Exception {
        try {
            webService().saveJgBuChargeRecordAry(feeAndRefundEntitys, feeAndRefundEntitys.size());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String scheduleName() {
        return Constants.SCH_SEND_FEEANDREFUND_SCHEDULE;
    }

    protected class FeeAndRefundParserConvert implements ParserConvert<List<JgBuChargeRecord>, List<FeeAndRefundEntity>> {
        @Override
        public List<JgBuChargeRecord> covert(List<FeeAndRefundEntity> param) {
            List<JgBuChargeRecord> jgBuChargeRecords = new ArrayList<JgBuChargeRecord>();
            JgBuChargeRecord jgBuChargeRecord = null;
            for (final FeeAndRefundEntity feeAndRefundEntity : param) {
                jgBuChargeRecord = new JgBuChargeRecord();
                jgBuChargeRecord.setOrganCode(feeAndRefundEntity.getOrgId());//1
                jgBuChargeRecord.setProjectCode(feeAndRefundEntity.getProjId());//2
                jgBuChargeRecord.setContractCode(feeAndRefundEntity.getContractId());//3
                jgBuChargeRecord.setChargeWay(feeAndRefundEntity.getChargeWay());//4
                jgBuChargeRecord.setChargeTypeCode(feeAndRefundEntity.getChargeType());//5
                jgBuChargeRecord.setChargeTime(xmlGregorianCalendar(feeAndRefundEntity.getChargeTime()));//6
                jgBuChargeRecord.setChargeMoney(feeAndRefundEntity.getChargeMoney());//7

                jgBuChargeRecord.setBatchDate(feeAndRefundEntity.getBatchDate());//8
                jgBuChargeRecords.add(jgBuChargeRecord);
            }
            return jgBuChargeRecords;
        }
    }
}
