package com.supervise.schedule.job;

import com.supervise.common.ParserConvert;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.mapper.BankCreditMapper;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuBankCredit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${admin} on 2018/2/3.
 */
@Component
public class BankTrustSenderSchedule extends AbstractSenderSchedule<List<JgBuBankCredit>> {
    private final Logger logger = LoggerFactory.getLogger(BankTrustSenderSchedule.class);
    @Autowired
    private BankCreditMapper bankCreditMapper;

    @Override
    public List<JgBuBankCredit> loadSenderData(String batchDate) {
        Example example = new Example(BankCreditEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("batch_date",batchDate);
        List<BankCreditEntity> bankCreditEntities = bankCreditMapper.selectByExample(example);
        return CollectionUtils.isEmpty(bankCreditEntities) ? null : new BankCreditParserConvert().covert(bankCreditEntities);
    }

    @Override
    public boolean checkData(List<JgBuBankCredit> bankCreditEntities) {
        return CollectionUtils.isEmpty(bankCreditEntities);
    }

    @Override
    public boolean senderData(List<JgBuBankCredit> bankCreditEntities) throws Exception {
        try {
            webService().saveJgBuBankCreditInfoAry(bankCreditEntities, bankCreditEntities.size());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String scheduleName() {
        return "BankCredit-Data";
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
