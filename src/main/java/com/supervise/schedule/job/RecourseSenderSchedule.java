package com.supervise.schedule.job;

import com.supervise.common.ParserConvert;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.mapper.RecourseMapper;
import com.supervise.schedule.AbstractSenderSchedule;
import com.supervise.webservice.JgBuReplevyInfo;
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
public class RecourseSenderSchedule extends AbstractSenderSchedule<List<JgBuReplevyInfo>> {

    private final Logger logger = LoggerFactory.getLogger(RecourseSenderSchedule.class);
    @Autowired
    private RecourseMapper recourseMapper;

    @Override
    public List<JgBuReplevyInfo> loadSenderData(String batchDate) {
        Example example = new Example(RecourseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("batch_date",batchDate);
        List<RecourseEntity> recourseEntitys = recourseMapper.selectByExample(example);
        return CollectionUtils.isEmpty(recourseEntitys) ? null : new RecourseParserConvert().covert(recourseEntitys);
    }

    @Override
    public boolean checkData(List<JgBuReplevyInfo> recourseEntitys) {
        return CollectionUtils.isEmpty(recourseEntitys);
    }

    @Override
    public boolean senderData(List<JgBuReplevyInfo> recourseEntitys) throws Exception {
        try {
            webService().saveJgBuReplevyInfoAry(recourseEntitys, recourseEntitys.size());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String scheduleName() {
        return "Recourse-Data";
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

                jgBuReplevyInfo.setBatchDate(recourseEntity.getBatchDate());//6
                jgBuReplevyInfos.add(jgBuReplevyInfo);
            }
            return jgBuReplevyInfos;
        }
    }
}
