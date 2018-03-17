package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.CompensatoryDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuReplaceInfo;
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
public class CompensatorySenderSchedule extends AbstractSenderSchedule<JgBuReplaceInfo> {

    private final Logger logger = LoggerFactory.getLogger(CompensatorySenderSchedule.class);
    @Autowired
    private CompensatoryDao compensatoryDao;

    @Autowired
    private TaskStatusDao taskStatusDao;

    private List<CompensatoryEntity> compensatoryEntitys = new ArrayList<CompensatoryEntity>();
    @Override
    public List<JgBuReplaceInfo> loadSenderData(String batchDate) {
//        Example example = new Example(CompensatoryEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);
        queryCondition.getValueList().add(batchDate);
        compensatoryEntitys = compensatoryDao.queryCompensatoryByCondition(queryCondition);
        return CollectionUtils.isEmpty(compensatoryEntitys) ? null : new CompensatoryParserConvert().covert(compensatoryEntitys);
    }

    @Override
    public boolean checkData(List<JgBuReplaceInfo> compensatoryEntities) {
        return CollectionUtils.isEmpty(compensatoryEntities);
    }

    @Override
    public boolean senderData(List<JgBuReplaceInfo> compensatoryEntities) throws Exception {
        long ret = -1;
        try {
            ret = webService().saveJgBuReplaceInfoAry(compensatoryEntities, compensatoryEntities.size());
            logger.info("Compesatory webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("Compesatory webSer Exception:"+e.getMessage());
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
        for(CompensatoryEntity compensatoryEntity : compensatoryEntitys){
            compensatoryEntity.setSendStatus(status);
            compensatoryDao.updateCompensatory(compensatoryEntity);
        }
    }

    @Override
    public void updateTaskStatus(String resultCode){
        String dataType = String.valueOf(DataType.SUPERVISE_REPLACE_DATA.getDataLevel());
        String dataName =DataType.SUPERVISE_REPLACE_DATA.getDataName();
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
        return Constants.SCH_SEND_COMPENSATORY_SCHEDULE;
    }

    protected class CompensatoryParserConvert implements ParserConvert<List<JgBuReplaceInfo>, List<CompensatoryEntity>> {
        @Override
        public List<JgBuReplaceInfo> covert(List<CompensatoryEntity> param) {
            List<JgBuReplaceInfo> jgBuReplaceInfos = new ArrayList<JgBuReplaceInfo>();
            JgBuReplaceInfo jgBuReplaceInfo = null;
            for (final CompensatoryEntity compensatoryEntity : param) {
                jgBuReplaceInfo = new JgBuReplaceInfo();
                jgBuReplaceInfo.setOrganCode(compensatoryEntity.getOrgId());//1
                jgBuReplaceInfo.setProjectCode(compensatoryEntity.getProjId());//2
                jgBuReplaceInfo.setContractCode(compensatoryEntity.getContractId());//3
                jgBuReplaceInfo.setReplaceDate(xmlGregorianCalendar(compensatoryEntity.getReplaceDate()));//4
                jgBuReplaceInfo.setReplaceMoney(compensatoryEntity.getReplaceMoney());//5

                jgBuReplaceInfo.setBatchDate(compensatoryEntity.getBatchDate());//6
                jgBuReplaceInfos.add(jgBuReplaceInfo);
            }
            return jgBuReplaceInfos;
        }
    }
}
