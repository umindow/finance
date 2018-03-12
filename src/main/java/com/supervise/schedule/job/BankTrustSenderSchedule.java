package com.supervise.schedule.job;

import com.supervise.common.Constants;
import com.supervise.common.ParserConvert;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuBankCredit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${admin} on 2018/2/3.
 */
@Component
public class BankTrustSenderSchedule extends AbstractSenderSchedule<JgBuBankCredit> {
    private final Logger logger = LoggerFactory.getLogger(BankTrustSenderSchedule.class);
    @Autowired
    private BankCreditDao bankCreditDao;
    private List<BankCreditEntity> bankCreditEntitys = new ArrayList<BankCreditEntity>();
    @Override
    public List<JgBuBankCredit> loadSenderData(String batchDate) {
//        Example example = new Example(BankCreditEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("batch_date",batchDate);

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.getColumnList().add("sendStatus");
        queryCondition.getColumnList().add("batch_date");

        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
        queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

        queryCondition.getValueList().add(Constants.DATA_READY_SEND);
        queryCondition.getValueList().add(batchDate);

        this.bankCreditEntitys = bankCreditDao.queryBankCreditByCondition(queryCondition);
        return CollectionUtils.isEmpty(bankCreditEntitys) ? null : new BankCreditParserConvert().covert(bankCreditEntitys);
    }

    @Override
    public boolean checkData(List<JgBuBankCredit> bankCreditEntities) {
        return CollectionUtils.isEmpty(bankCreditEntities);
    }

    @Override
    public boolean senderData(List<JgBuBankCredit> bankCreditEntities) throws Exception {
        long ret = -1;
        try {
            ret =  webService().saveJgBuBankCreditInfoAry(bankCreditEntities, bankCreditEntities.size());
            logger.info("BankCredit webSer Ret:"+ret);
        } catch (Exception e) {
            logger.error("BankCredit webSer Exception:"+e.getMessage());
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
        for(BankCreditEntity bankCreditEntity : bankCreditEntitys){
            bankCreditEntity.setSendStatus(status);
            bankCreditDao.updateBankCredit(bankCreditEntity);
        }
    }

    @Override
    public String scheduleName() {
        return Constants.SCH_SEND_BANKCREDIT_SCHEDULE;
    }

    protected class BankCreditParserConvert implements ParserConvert<List<JgBuBankCredit>, List<BankCreditEntity>> {
        @Override
        public List<JgBuBankCredit> covert(List<BankCreditEntity> param) {
            List<JgBuBankCredit> jgBuBankCredits = new ArrayList<JgBuBankCredit>();
            JgBuBankCredit jgBuBankCredit = null;
            for (final BankCreditEntity bankCreditEntity : param) {
                jgBuBankCredit = new JgBuBankCredit();
                jgBuBankCredit.setBailMoney(bankCreditEntity.getBailMoney());//1
                jgBuBankCredit.setBailScale(bankCreditEntity.getBailScale());//2
                jgBuBankCredit.setBankCode(bankCreditEntity.getBankId());//3
                jgBuBankCredit.setBatchDate(bankCreditEntity.getBatchDate());//4
                jgBuBankCredit.setBlowupMulpitle(bankCreditEntity.getBlowupMulpitle());//5
                jgBuBankCredit.setCreditEndDate(xmlGregorianCalendar(bankCreditEntity.getCreditEndDate()));//6
                jgBuBankCredit.setCreditMoney(bankCreditEntity.getCreditMoney());//7
                jgBuBankCredit.setCreditStatus(bankCreditEntity.getCreditStatus());//8
                jgBuBankCredit.setCreditTypeCode(bankCreditEntity.getCreditTypeId());//9
                jgBuBankCredit.setCreditStartDate(xmlGregorianCalendar(bankCreditEntity.getCreditStartDate()));//19
//                jgBuBankCredit.setId(bankCreditEntity.getId());//10
                jgBuBankCredit.setIsForCredit(bankCreditEntity.getIsForCredit());//11
                jgBuBankCredit.setItemLean(bankCreditEntity.getItemLean());//12
                jgBuBankCredit.setLeaveMoney(bankCreditEntity.getLeaveMoney());//13
                jgBuBankCredit.setMainBankCode(bankCreditEntity.getMainBankId());//14
                jgBuBankCredit.setOrganCode(bankCreditEntity.getOrgId());//15
                jgBuBankCredit.setPrimaryId(bankCreditEntity.getPrimaryId());//16
                jgBuBankCredit.setRemark(bankCreditEntity.getRemark());//17
                jgBuBankCredit.setSingleMoneyLimit(bankCreditEntity.getSingleMoneyLimit());//18
                jgBuBankCredits.add(jgBuBankCredit);
            }
            return jgBuBankCredits;
        }
    }
}
