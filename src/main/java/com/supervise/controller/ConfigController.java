package com.supervise.controller;

import com.alibaba.fastjson.JSON;
import com.supervise.common.Constants;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.controller.vo.ConfigVo;
import com.supervise.dao.mysql.entity.ConfigEntity;
import com.supervise.dao.mysql.mapper.ConfigMapper;
import com.supervise.schedule.JobInfo;
import com.supervise.schedule.QuartzScheduleExecutor;
import com.supervise.schedule.QuartzScheduleManager;
import com.supervise.support.Result;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                JobInfo job = JSON.parseObject(configEntity.getConfigContent(), JobInfo.class);
                String cron = job.getCron();
                if(StringUtils.isEmpty(job.getScheduleTime())){
                    String time  = createScheduleTime(cron);
                    job.setScheduleTime(time);
                }
                configVo.setJobInfo(job);
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
                || null == configVo.getJobInfo().getScheduleTime()){
            return Result.fail("添加失败,Job名及定时时间不能为空.");
        }
        String scheduleTime = configVo.getJobInfo().getScheduleTime();
        if(!isValidTimeExpression(scheduleTime)){
            return Result.fail("定时时间不符合规范.");
        }
        String cron = createCron(scheduleTime);
        if(!isValidCronExpression(cron)){
            return Result.fail("定时时间不符合规范.");
        }
        configVo.getJobInfo().setCron(cron);
        if(StringUtils.isEmpty(configVo.getJobInfo().getJobGroup())){
            configVo.getJobInfo().setJobGroup(JobInfo.DEFAULT_JOB_GROUP);
        }
        if(StringUtils.isEmpty(configVo.getJobInfo().getJobTriggerGroup())){
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

    private static boolean isValidCronExpression(final String cronExpression){
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

    private static boolean isValidTimeExpression(final String timeExpression){
        // 验证规则
        String regEx = "^((\\d)|(1\\d)|(2[0-4]))$";//支持0
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher matcher = pattern.matcher(timeExpression);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();

        return rs;
    }

    /**
     * 根据表达式获取定时表达式:
     * @param schedule
     * @return
     */
    private String createCron(String schedule){
        StringBuffer cron = new StringBuffer();
        if(!StringUtils.isEmpty(schedule)){
            cron.append("0");
            cron.append(Constants.SPACE);
            cron.append("0");
            cron.append(Constants.SPACE);
            cron.append(schedule);
            cron.append(Constants.SPACE);
            cron.append(Constants.AST);
            cron.append(Constants.SPACE);
            cron.append(Constants.AST);
            cron.append(Constants.SPACE);
            cron.append(Constants.QUESTION);
        }
        return cron.toString();
    }

    private  String  createScheduleTime(String cron){
        String time = "";
        if(!StringUtils.isEmpty(cron)&&isValidCronExpression(cron)){
            String param [] = cron.split(Constants.SPACE);
            if(param.length>=3){
                time = param[2];
            }
        }
        return time;
    }
}
