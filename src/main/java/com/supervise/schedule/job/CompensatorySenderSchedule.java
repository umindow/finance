package com.supervise.schedule.job;

import com.supervise.common.ParserConvert;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.mapper.CompensatoryMapper;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuReplaceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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
public class CompensatorySenderSchedule extends AbstractSenderSchedule<JgBuReplaceInfo> {

    private final Logger logger = LoggerFactory.getLogger(CompensatorySenderSchedule.class);
    @Autowired
    private CompensatoryMapper compensatoryMapper;

    @Override
    public List<JgBuReplaceInfo> loadSenderData(String batchDate) {
        Example example = new Example(CompensatoryEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("batch_date",batchDate);
        List<CompensatoryEntity> compensatoryEntities = compensatoryMapper.selectByExample(example);
        return CollectionUtils.isEmpty(compensatoryEntities) ? null : new CompensatoryParserConvert().covert(compensatoryEntities);
    }

    @Override
    public boolean checkData(List<JgBuReplaceInfo> compensatoryEntities) {
        return CollectionUtils.isEmpty(compensatoryEntities);
    }

    @Override
    public boolean senderData(List<JgBuReplaceInfo> compensatoryEntities) throws Exception {
        try {
            webService().saveJgBuReplaceInfoAry(compensatoryEntities, compensatoryEntities.size());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String scheduleName() {
        return "Compensatory-Data";
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
