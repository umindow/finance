package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuRepayDetail;
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
public class RepaymentSenderSchedule extends AbstractSenderSchedule<JgBuRepayDetail> {

    private final Logger logger = LoggerFactory.getLogger(RepaymentSenderSchedule.class);
    @Autowired
    private RepaymentDao repaymentDao;

    @Autowired
    private TaskStatusDao taskStatusDao;

    private List<RepaymentEntity> repaymentEntitys = new ArrayList<RepaymentEntity>();

    @Override
    public List<JgBuRepayDetail> loadSenderData(String batchDate) {
//        Example example = new Example(RepaymentEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);
//        List<RepaymentEntity> repaymentEntitys = repaymentMapper.selectByExample(example);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);
        queryCondition.getValueList().add(batchDate);
        this.repaymentEntitys = repaymentDao.queryRepaymentByCondition(queryCondition);
        return CollectionUtils.isEmpty(repaymentEntitys) ? null : new RepaymentParserConvert().covert(repaymentEntitys);
    }

    @Override
    public boolean checkData(List<JgBuRepayDetail> repaymentEntitys) {
        return CollectionUtils.isEmpty(repaymentEntitys);
    }

    @Override
    public boolean senderData(List<JgBuRepayDetail> repaymentEntitys) throws Exception {
        long ret = -1;
        logger.info("Repayment webSer send size:"+repaymentEntitys.size());
        try {
            ret = webService().saveJgBuRepayDetailAry(repaymentEntitys, repaymentEntitys.size());
            logger.info("Repayment webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("Repayment webSer Exception:"+e.getMessage());
            e.printStackTrace();
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
        for(RepaymentEntity repaymentEntity : repaymentEntitys){
            repaymentEntity.setSendStatus(status);
            repaymentDao.updateRepayment(repaymentEntity);
        }
    }

    @Override
    public void updateTaskStatus(String resultCode){
        String dataType = String.valueOf(DataType.SUPERVISE_REBACK_DATA.getDataLevel());
        String dataName =DataType.SUPERVISE_REBACK_DATA.getDataName();
        String option = "1";
        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setDataName(dataName);
        taskStatusEntity.setDataType(dataType);
        taskStatusEntity.setOpType(option);
        taskStatusEntity.setResult(resultCode);
        this.taskStatusDao.insertTaskStatusToMiddleDB(taskStatusEntity);
    }

    @Override
    public String scheduleName() {
        return Constants.SCH_SEND_REPAYMENT_SCHEDULE;
    }

    protected class RepaymentParserConvert implements ParserConvert<List<JgBuRepayDetail>, List<RepaymentEntity>> {
        @Override
        public List<JgBuRepayDetail> covert(List<RepaymentEntity> param) {
            List<JgBuRepayDetail> jgBuRepayDetails = new ArrayList<JgBuRepayDetail>();
            JgBuRepayDetail jgBuRepayDetail = null;
            for (final RepaymentEntity repaymentEntity : param) {
                jgBuRepayDetail = new JgBuRepayDetail();
                jgBuRepayDetail.setOrganCode(repaymentEntity.getOrgId());//1
                jgBuRepayDetail.setProjectCode(repaymentEntity.getProjId());//2
                jgBuRepayDetail.setContractCode(repaymentEntity.getContractId());//3
                jgBuRepayDetail.setRepayDate(xmlGregorianCalendar(repaymentEntity.getRepayDate()));//4
                jgBuRepayDetail.setPrincipal(repaymentEntity.getPrincipal());//5
                jgBuRepayDetail.setInterest(repaymentEntity.getInterest());//6
                jgBuRepayDetail.setPunishMoney(repaymentEntity.getPunishMoney());//7

                jgBuRepayDetail.setBatchDate(repaymentEntity.getBatchDate());//8
                jgBuRepayDetails.add(jgBuRepayDetail);
            }
            return jgBuRepayDetails;
        }
    }
}
