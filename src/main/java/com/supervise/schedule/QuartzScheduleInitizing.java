package com.supervise.schedule;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.supervise.config.role.DataType;
import com.supervise.dao.mysql.entity.ConfigEntity;
import com.supervise.dao.mysql.mapper.ConfigMapper;
import com.supervise.support.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by xishui.hb on 2018/2/2 上午10:26.
 *
 * @author xishui
 *         Description:
 *         Modify Record
 *         ----------------------------------------
 *         User    |    Time    |    Note
 */
@Component
public class QuartzScheduleInitizing{
    private final Logger logger = LoggerFactory.getLogger(QuartzScheduleInitizing.class);
    @Autowired
    private ConfigMapper configMapper;
    public void initDbSchedule() {
        //加载数据库中已经有的任务列表
        List<ConfigEntity> configs = configMapper.selectAll();
        if (CollectionUtils.isEmpty(configs)) {
            logger.error("Non Job Config From DB loaded.");
            return;
        }
        for (final ConfigEntity configEntity : configs) {
            JobInfo jobInfo = null;
            try {
                jobInfo = JSON.parseObject(configEntity.getConfigContent(), JobInfo.class);
                QuartzScheduleManager.MANAGER.addJob(jobInfo.getJobName(), jobInfo.getJobGroup(), jobInfo.getJobTriggerGroup(), QuartzScheduleExecutor.class, jobInfo.getCron(), DataType.typeOfName(jobInfo.getJobDataType()));
            } catch (Exception e) {
                logger.error("Job Config Init to Quartz Fail. Parse JobInfo By Json Err.", e);
            }
        }
    }
}
