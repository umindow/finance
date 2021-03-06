package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.RecourseDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuReplevyInfo;
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
public class RecourseSenderSchedule extends AbstractSenderSchedule<JgBuReplevyInfo> {

    private final Logger logger = LoggerFactory.getLogger(RecourseSenderSchedule.class);
    @Autowired
    private RecourseDao recourseDao;

    @Autowired
    private TaskStatusDao taskStatusDao;

    private List<RecourseEntity> recourseEntitys = new ArrayList<RecourseEntity>();
    @Override
    public List<JgBuReplevyInfo> loadSenderData(String batchDate) {
//        Example example = new Example(RecourseEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);
        queryCondition.getValueList().add(batchDate);

        this.recourseEntitys = recourseDao.queryRecourseByCondition(queryCondition);
        return CollectionUtils.isEmpty(recourseEntitys) ? null : new RecourseParserConvert().covert(recourseEntitys);
    }

    @Override
    public boolean checkData(List<JgBuReplevyInfo> recourseEntitys) {
        return CollectionUtils.isEmpty(recourseEntitys);
    }

    @Override
    public boolean senderData(List<JgBuReplevyInfo> recourseEntitys) throws Exception {
        long ret = -1;
        try {
            ret = webService().saveJgBuReplevyInfoAry(recourseEntitys, recourseEntitys.size());
            logger.info("Recourse webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("Recourse webSer Exception:"+e.getMessage());
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
        for(RecourseEntity recourseEntity : recourseEntitys){
            recourseEntity.setSendStatus(status);
            recourseDao.updateRecourse(recourseEntity);
        }

    }

    @Override
    public void updateTaskStatus(String resultCode){
        String dataType = String.valueOf(DataType.SUPERVISE_TRACE_DATA.getDataLevel());
        String dataName =DataType.SUPERVISE_TRACE_DATA.getDataName();
        String option = "1";
        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setDataName(dataName);
        taskStatusEntity.setDataType(dataType);
        taskStatusEntity.setOpType(option);
        taskStatusEntity.setResult(resultCode);
        taskStatusEntity.setRemark(String.valueOf(recourseEntitys.size()));
        this.taskStatusDao.insertTaskStatusToMiddleDB(taskStatusEntity);
        recourseEntitys.clear();
    }

    @Override
    public String scheduleName() {
        return Constants.SCH_SEND_RECOURSE_SCHEDULE;
    }

    protected class RecourseParserConvert implements ParserConvert<List<JgBuReplevyInfo>, List<RecourseEntity>> {
        @Override
        public List<JgBuReplevyInfo> covert(List<RecourseEntity> param) {
            List<JgBuReplevyInfo> jgBuReplevyInfos = new ArrayList<JgBuReplevyInfo>();
            JgBuReplevyInfo jgBuReplevyInfo = null;
            for (final RecourseEntity recourseEntity : param) {
                jgBuReplevyInfo = new JgBuReplevyInfo();
                jgBuReplevyInfo.setOrganCode(recourseEntity.getOrgId());//1
                jgBuReplevyInfo.setProjectCode(recourseEntity.getProjId());//2
                jgBuReplevyInfo.setContractCode(recourseEntity.getContractId());//3
                jgBuReplevyInfo.setReplevyTypeCode(recourseEntity.getReplevyType());//4
                jgBuReplevyInfo.setReplevyDate(xmlGregorianCalendar(recourseEntity.getReplevyDate()));//5
                jgBuReplevyInfo.setReplevyMoney(recourseEntity.getReplevyMoney());//6

                jgBuReplevyInfo.setBatchDate(recourseEntity.getBatchDate());//7
                jgBuReplevyInfos.add(jgBuReplevyInfo);
            }
            return jgBuReplevyInfos;
        }
    }
}
