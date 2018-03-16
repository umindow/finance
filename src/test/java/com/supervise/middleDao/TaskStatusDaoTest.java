package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.TaskStatusEntity;
import com.supervise.dao.mysql.middleDao.TaskStatusDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskStatusDaoTest extends BaseTest{
	
	@Autowired
	private TaskStatusDao taskStatusDao ;

	
	@Test
	public void insertTaskStatusToMiddleDBTest(){

		List<TaskStatusEntity> resListToDB = new ArrayList<TaskStatusEntity>();
		for(int i =  0; i<5;i++ ){
			TaskStatusEntity entity = new TaskStatusEntity();
			entity.setCreateTime(new Date());
			entity.setOpTime(new Date());
			entity.setRemark("备注");
			entity.setDataName("银行授信");
			entity.setDataType("1");
			entity.setOpType("0");
			entity.setResult("1");
			resListToDB.add(entity);
		}
		for(TaskStatusEntity entity : resListToDB ){
			int id = this.taskStatusDao.insertTaskStatusToMiddleDB(entity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryTaskStatusByConditionTest(){
		Date date  = new Date();
		String option = "0";
		String result = "1";
		String dataType = "1";



		List<TaskStatusEntity> resListToDB = this.taskStatusDao.queryTaskStatusByCondition(date,option,dataType,result);

		Assert.assertEquals(5,resListToDB.size());
	}

	@Test
	public void queryTaskStatusByConditionALLTest(){
		Date date  = new Date();
		String option = "ALL";
		String result = "all";
		String dataType = "alL";



		List<TaskStatusEntity> resListToDB = this.taskStatusDao.queryTaskStatusByCondition(date,option,dataType,result);

		Assert.assertEquals(5,resListToDB.size());
	}

	@Test
	public void queryTaskStatusByConditionNullTest(){
		Date date  = new Date();
		String option = "ALL";
		String result = "2";
		String dataType = "alL";



		List<TaskStatusEntity> resListToDB = this.taskStatusDao.queryTaskStatusByCondition(date,option,dataType,result);

		Assert.assertEquals(0,resListToDB.size());
	}

}
