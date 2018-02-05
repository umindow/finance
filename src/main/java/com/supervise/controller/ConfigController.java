package com.supervise.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.config.role.RoleType;
import com.supervise.controller.vo.ConfigVo;
import com.supervise.dao.mysql.entity.ConfigEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.mapper.ConfigMapper;
import com.supervise.schedule.JobInfo;
import com.supervise.schedule.QuartzScheduleExecutor;
import com.supervise.schedule.QuartzScheduleManager;
import com.supervise.support.Result;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/2/2 下午3:05.
 *
 * @author xishui
 *         Description:
 *         Modify Record
 *         ----------------------------------------
 *         User    |    Time    |    Note
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigMapper configMapper;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listConfig() {
        ModelAndView view = new ModelAndView("pages/config/list");
        List<ConfigEntity> configs = configMapper.selectAll();
        List<ConfigVo> configVos = new ArrayList<ConfigVo>();
        if (!CollectionUtils.isEmpty(configs)) {
            for (final ConfigEntity configEntity : configs) {
                ConfigVo configVo = new ConfigVo();
                configVo.setConfigType(configEntity.getConfigType());
                configVo.setConfigTypeDesc(ConfigEntity.ConfigType.getConfigTypeDesc(configEntity.getConfigType()));
                configVo.setId(configEntity.getId());
                configVo.setJobInfo(JSON.parseObject(configEntity.getConfigContent(), JobInfo.class));
                configVos.add(configVo);
            }
        }
        view.addObject("configs", configVos);
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView view = new ModelAndView("pages/config/add");
        view.addObject("configTypes", Arrays.asList(ConfigEntity.ConfigType.values()));
        view.addObject("dataRoles", Arrays.asList(DataType.values()));
        return view;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(ConfigVo configVo) {
        if(null == configVo || null ==configVo.getJobInfo() || null == configVo.getJobInfo().getJobName()
                || null == configVo.getJobInfo().getCron()){
            return Result.fail("添加失败,Job名及Cron表达式不能为空.");
        }
        if(!isValidExpression(configVo.getJobInfo().getCron())){
            return Result.fail("Cron表达式不符合规范.");
        }
        if(null == configVo.getJobInfo().getJobGroup()){
            configVo.getJobInfo().setJobGroup(JobInfo.DEFAULT_JOB_GROUP);
        }
        if(null == configVo.getJobInfo().getJobTriggerGroup()){
            configVo.getJobInfo().setJobTriggerGroup(JobInfo.DEFAULT_JOB_TRIGGER_GROUP);
        }
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setConfigContent(JSON.toJSONString(configVo.getJobInfo()));
        configEntity.setCreateTime(new Date());
        configEntity.setCreateUserId(SessionUser.INSTANCE.getCurrentUser().getId());
        configEntity.setConfigType(configVo.getConfigType());
        configMapper.insert(configEntity);
        QuartzScheduleManager.MANAGER.addJob(configVo.getJobInfo().getJobName(),
                configVo.getJobInfo().getJobGroup(),
                configVo.getJobInfo().getJobTriggerGroup(),
                QuartzScheduleExecutor.class,
                configVo.getJobInfo().getCron(),
                DataType.typeOfName(configVo.getJobInfo().getJobDataType()));
        return Result.success();
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(long id) {
        ConfigEntity configEntity = configMapper.selectByPrimaryKey(id);
        if (null == configEntity) {
            return Result.fail("配置不存在.");
        }
        configMapper.deleteByPrimaryKey(id);
        JobInfo jobInfo = JSON.parseObject(configEntity.getConfigContent(), JobInfo.class);
        QuartzScheduleManager.MANAGER.removeJob(jobInfo.getJobName(), jobInfo.getJobGroup(), jobInfo.getJobTriggerGroup());
        return Result.success();
    }

    private static boolean isValidExpression(final String cronExpression){
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
