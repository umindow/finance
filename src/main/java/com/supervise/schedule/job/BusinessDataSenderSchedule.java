package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/2/28 下午3:37.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Component
public class BusinessDataSenderSchedule extends AbstractSenderSchedule<JgBuProjectInfo>{

    private final Logger logger = LoggerFactory.getLogger(BusinessDataSenderSchedule.class);

    @Autowired
    private BusinessDataDao businessDataDao;

    @Autowired
    private TaskStatusDao taskStatusDao;

    private List<BusinessDataEntity> businessDataEntities = new ArrayList<BusinessDataEntity>();

    @Override
    public List<JgBuProjectInfo> loadSenderData(String batchDate) {
//        Example example = new Example(BankCreditEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);//待发送状态的
        queryCondition.getValueList().add(batchDate);
        this.businessDataEntities = businessDataDao.queryBusinessDataByCondition(queryCondition);
        return CollectionUtils.isEmpty(businessDataEntities) ? null : new BusinessDataParserConvert().covert(businessDataEntities);
    }

    @Override
    public boolean checkData(List<JgBuProjectInfo> jgBuProjectInfos) {
        return CollectionUtils.isEmpty(jgBuProjectInfos);
    }

    @Override
    public boolean senderData(List<JgBuProjectInfo> jgBuProjectInfos) throws Exception {
        long ret = -1;
        try {
            ret = webService().saveJgBuProjectInfoAry(jgBuProjectInfos, jgBuProjectInfos.size());
            logger.info("Business webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("Business webSer Exception:" + e.getMessage());
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
        for(BusinessDataEntity businessDataEntity : businessDataEntities){
            businessDataEntity.setSendStatus(status);
            businessDataDao.updateBusinessData(businessDataEntity);
        }
    }

    @Override
    public void updateTaskStatus(String resultCode){
        String dataType = String.valueOf(DataType.SUPERVISE_BIZ_DATA.getDataLevel());
        String dataName =DataType.SUPERVISE_BIZ_DATA.getDataName();
        String option = "1";
        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setDataName(dataName);
        taskStatusEntity.setDataType(dataType);
        taskStatusEntity.setOpType(option);
        taskStatusEntity.setResult(resultCode);
        taskStatusEntity.setRemark(String.valueOf(businessDataEntities.size()));
        this.taskStatusDao.insertTaskStatusToMiddleDB(taskStatusEntity);
        businessDataEntities.clear();
    }

    @Override
    public String scheduleName() {
        return Constants.SCH_SEND_BUSINESS_SCHEDULE;
    }

    protected class BusinessDataParserConvert implements ParserConvert<List<JgBuProjectInfo>, List<BusinessDataEntity>> {
        @Override
        public List<JgBuProjectInfo> covert(List<BusinessDataEntity> param) {
            List<JgBuProjectInfo> jgBuBusinesses = new ArrayList<JgBuProjectInfo>();
            JgBuProjectInfo jgBuProjectInfo = null;
            for (final BusinessDataEntity businessDataEntity : param) {
                jgBuProjectInfo = new JgBuProjectInfo();
                jgBuProjectInfo.setOrganCode(businessDataEntity.getOrgId());//1
                jgBuProjectInfo.setProjectCode(businessDataEntity.getProjId());//2
                jgBuProjectInfo.setClientType(businessDataEntity.getClientType());//3
                jgBuProjectInfo.setClientCode(businessDataEntity.getClientId());//4
                jgBuProjectInfo.setClientName(businessDataEntity.getClientName());//5

                jgBuProjectInfo.setIdCardType(businessDataEntity.getIDCardType());//6
                jgBuProjectInfo.setIdCard(businessDataEntity.getIDCard());//7
                jgBuProjectInfo.setCallingFirst(businessDataEntity.getCallingFirst());//8
                jgBuProjectInfo.setCallingSecond(businessDataEntity.getCallingSecond());//9
                jgBuProjectInfo.setAreaFirst(businessDataEntity.getAreaFirst());//10
                jgBuProjectInfo.setAreaSecond(businessDataEntity.getAreaSecond());//11
                jgBuProjectInfo.setAreaThird(businessDataEntity.getAreaThird());//12

                jgBuProjectInfo.setCompanyScale(businessDataEntity.getCompanyScale());//13
                jgBuProjectInfo.setIsFarming(businessDataEntity.getIsFarming());//14
                jgBuProjectInfo.setBusinessType(businessDataEntity.getBusinessType());//15
                jgBuProjectInfo.setContractMoney(businessDataEntity.getContractMoney());//16
                jgBuProjectInfo.setLoanMoney(businessDataEntity.getLoanMoney());//17
                jgBuProjectInfo.setLoanRate(businessDataEntity.getLoanRate());//18
                jgBuProjectInfo.setAssureRate(businessDataEntity.getAssureRate());//19

                jgBuProjectInfo.setLoanDate(xmlGregorianCalendar(businessDataEntity.getLoanDate()));//20
                jgBuProjectInfo.setContractEndDate(xmlGregorianCalendar(businessDataEntity.getContractEndDate()));//21
                jgBuProjectInfo.setRepayTypeCode(businessDataEntity.getRepayType());//22
                jgBuProjectInfo.setPledgeTypeCode(businessDataEntity.getPledgeType());//23
                jgBuProjectInfo.setApproveOption(businessDataEntity.getApproveOption());//24
                jgBuProjectInfo.setBankCreditPrimaryId(businessDataEntity.getBankCreditPrimaryId());//25
                jgBuProjectInfo.setBankCode(businessDataEntity.getCoBankId());//26

                jgBuProjectInfo.setProjectStatus(businessDataEntity.getProjSatus());//27
                jgBuProjectInfo.setAssurePerson(businessDataEntity.getAssurePerson());//28
                jgBuProjectInfo.setPledgeWorth(businessDataEntity.getPledgeWorth());//29
                jgBuProjectInfo.setIsImpawn(businessDataEntity.getIsImpawn());//30
                jgBuProjectInfo.setAcceptDate(xmlGregorianCalendar(businessDataEntity.getAcceptDate()));//31
                jgBuProjectInfo.setContractCode(businessDataEntity.getContractId());//32
                jgBuProjectInfo.setClientBailMoney(businessDataEntity.getClientBailMoney());//33

                jgBuProjectInfo.setOutBailMoney(businessDataEntity.getOutBailMoney());//34
                jgBuProjectInfo.setCapitalBelong(businessDataEntity.getCapitalBelong());//35
                jgBuProjectInfo.setProjectEndDate(xmlGregorianCalendar(businessDataEntity.getProjEndDate()));//36
                jgBuProjectInfo.setBatchDate(businessDataEntity.getBatchDate());//37
                jgBuBusinesses.add(jgBuProjectInfo);
            }
            return jgBuBusinesses;
        }
    }
}
