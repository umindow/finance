package com.supervise.dao.mysql.middleDao;

import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.mapper.TaskStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository("TaskStatusDao")
public class TaskStatusDao {
	
	@Autowired
	private TaskStatusMapper taskStatusMapper;

	/**
	 * 任务状态记录插入中间库数据表
	* insertTaskStatusToMiddleDB
	* @param taskStatusEntity 还款记录
	* @return int 主键ID号
	 */
    public int insertTaskStatusToMiddleDB(TaskStatusEntity taskStatusEntity){
    	int id = -1;
    	if(null!=taskStatusEntity){
			taskStatusEntity.setId(0L);
			String dateStr = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).format(new Date());
			Date newDate = DateUtils.String2Date(dateStr, Constants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
			taskStatusEntity.setCreateTime(newDate);
			taskStatusEntity.setOpTime(newDate);
			id= this.taskStatusMapper.insert(taskStatusEntity);
    	}
    	return id;
    }

	/**
	 * 按照指定条件从中间库中查询任务执行信息
	 * @param date
	 * @param option
	 * @param dataType
	 * @param result
	 * @return List<TaskStatusEntity>
	 */
	public List<TaskStatusEntity> queryTaskStatusByCondition(String date,String option,String dataType,String result){

		Example example = new Example(TaskStatusEntity.class);
		Example.Criteria fcriteria = example.createCriteria();
		if(date!=null){
			String datemin = date+Constants.DAY_MIN_DATE;
			String datemax = date+Constants.DAY_MAX_DATE;
			Date mixd = DateUtils.String2Date(datemin,Constants.YYYY_MM_DD_HH_MM_SS,Locale.ENGLISH);
			Date maxd = DateUtils.String2Date(datemax,Constants.YYYY_MM_DD_HH_MM_SS,Locale.ENGLISH);
			fcriteria.andBetween("opTime",mixd,maxd);
		}
		if(!StringUtils.isEmpty(option)&&!Constants.QUERY_SELECT_ALL.equalsIgnoreCase(option)){
			fcriteria.andEqualTo("opType", option);
		}
		if(!StringUtils.isEmpty(dataType)&&!Constants.QUERY_SELECT_ALL.equalsIgnoreCase(dataType)){
			fcriteria.andEqualTo("dataType", dataType);
		}
		if(!StringUtils.isEmpty(result)&&!Constants.QUERY_SELECT_ALL.equalsIgnoreCase(result)){
			fcriteria.andEqualTo("result", result);
		}
		List<TaskStatusEntity> responseList  = this.taskStatusMapper.selectByExample(example);
		return responseList;
	}

}
