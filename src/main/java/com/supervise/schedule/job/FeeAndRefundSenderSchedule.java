package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
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

    @Autowired
    private TaskStatusDao taskStatusDao;

    private List<FeeAndRefundEntity> feeAndRefundEntitys = new ArrayList<FeeAndRefundEntity>();

    @Override
    public List<JgBuChargeRecord> loadSenderData(String batchDate) {
//        Example example = new Example(FeeAndRefundEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);
        queryCondition.getValueList().add(batchDate);
        this.feeAndRefundEntitys = feeAndRefundDao.queryFeeAndRefundByCondition(queryCondition);
        return CollectionUtils.isEmpty(feeAndRefundEntitys) ? null : new FeeAndRefundParserConvert().covert(feeAndRefundEntitys);
    }

    @Override
    public boolean checkData(List<JgBuChargeRecord> feeAndRefundEntitys) {

        return CollectionUtils.isEmpty(feeAndRefundEntitys);
    }

    @Override
    public boolean senderData(List<JgBuChargeRecord> feeAndRefundEntitys) throws Exception {
        long ret = -1;
        try {
            ret = webService().saveJgBuChargeRecordAry(feeAndRefundEntitys, feeAndRefundEntitys.size());
            logger.info("FeeAndRefund webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("FeeAndRefund webSer Exception:"+e.getMessage());
            return false;
        }
        //如果返回值不是1 ，则发送失败。
        if(Constants.WEBSERV_RES_SUCESS!=ret){
            return false;
        }
        return true;
    }

    @Override
    public void updateDataStatus(String status){
        //将已发送成的数据状态更新为发送成功
        for(FeeAndRefundEntity feeAndRefundEntity : feeAndRefundEntitys){
            feeAndRefundEntity.setSendStatus(status);
            feeAndRefundDao.updateFeeAndRefund(feeAndRefundEntity);
        }

    }

    @Override
    public void updateTaskStatus(String resultCode){
        String dataType = String.valueOf(DataType.SUPERVISE_FEE_DATA.getDataLevel());
        String dataName =DataType.SUPERVISE_FEE_DATA.getDataName();
        String option = "1";
        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setDataName(dataName);
        taskStatusEntity.setDataType(dataType);
        taskStatusEntity.setOpType(option);
        taskStatusEntity.setResult(resultCode);
        taskStatusEntity.setRemark(String.valueOf(feeAndRefundEntitys.size()));
        this.taskStatusDao.insertTaskStatusToMiddleDB(taskStatusEntity);
        feeAndRefundEntitys.clear();
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
