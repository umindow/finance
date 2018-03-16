package com.supervise.controller;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.controller.vo.TaskStatusVo;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午5:30.
 * 数据操作controller，查询/编辑/trigger推送
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@RestController
@RequestMapping("/log")
public class LogController {

    private final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private TaskStatusDao taskStatusDao ;

    @Getter
    private UserEntity userEntity;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "date", required = false) String date,
                             @RequestParam(value = "dataType", required = true) Integer dataType,
                             @RequestParam(value = "optionType", required = true) String optionType,
                             @RequestParam(value = "resultcode", required = true) String resultcode) {
        ModelAndView view = new ModelAndView("pages/log/list");
        String dateTypeStr = dataType.toString();
        if (date == null || "".equals(date)) {
            date = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        }
        List<TaskStatusVo> voList = new ArrayList<TaskStatusVo>();
        List<TaskStatusEntity> entities = this.taskStatusDao.queryTaskStatusByCondition(date,dateTypeStr,optionType,resultcode);
        if(!CollectionUtils.isEmpty(entities)){
            for(TaskStatusEntity taskStatusEntity : entities){
                TaskStatusVo  taskStatusVo = new TaskStatusVo();
                taskStatusVo.setDataName(taskStatusEntity.getDataName());
                if("1".equalsIgnoreCase(taskStatusEntity.getOpType())){
                    taskStatusVo.setOptionType("上报数据");
                }else if("0".equalsIgnoreCase(taskStatusEntity.getOpType())){
                    taskStatusVo.setOptionType("同步数据");
                }
                if("-1".equalsIgnoreCase(taskStatusEntity.getOpType())){
                    taskStatusVo.setOptionType("操作失败");
                }else if("0".equalsIgnoreCase(taskStatusEntity.getOpType())){
                    taskStatusVo.setOptionType("操作成功");
                }

                if(taskStatusEntity.getOpTime()!=null){
                    String time = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(taskStatusEntity.getOpTime());
                    taskStatusVo.setOptime(time);
                }
                voList.add(taskStatusVo);
            }

        }
        view.addObject("tasks", voList);
        return view;
    }
}
