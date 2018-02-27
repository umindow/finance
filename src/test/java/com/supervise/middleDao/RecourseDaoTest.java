package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.RecourseEntity;
import com.supervise.dao.mysql.middleDao.RecourseDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecourseDaoTest extends BaseTest{
	
	@Autowired
	private RecourseDao recourseDao ;

	
	@Test
	public void insertRecourseToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<RecourseEntity> resListToDB = new ArrayList<RecourseEntity>();
		for(int i =  0; i<5;i++ ){
			RecourseEntity entity = new RecourseEntity();
			entity.setOrgId("1");
			entity.setProjId("2");
			entity.setContractId("1");
			entity.setReplevyType("10");
			entity.setReplevyDate(new Date());
			entity.setReplevyMoney(new BigDecimal(1000 + 100 * i));
			entity.setBatchDate(batchDate);
			resListToDB.add(entity);
		}
		for(RecourseEntity recourseEntity : resListToDB ){
			int id = this.recourseDao.insertRecourseToMiddleDB(recourseEntity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryRecourseByConditionTest(){
		String batchDate = "2018-02-24";
		String org_id = "1";
		String proj_id = "2";
		String datestr = "2018-02-28";

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("org_id");
		queryCondition.getColumnList().add("proj_id");
		queryCondition.getColumnList().add("replevy_date");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(org_id);
		queryCondition.getValueList().add(proj_id);
		queryCondition.getValueList().add(datestr);
		queryCondition.getValueList().add(batchDate);


		List<RecourseEntity> resListToDB = this.recourseDao.queryRecourseByCondition(queryCondition);

		Assert.assertEquals(5,resListToDB.size());
	}

	@Test
	public void updateRecourseTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<RecourseEntity> resListToDB =  this.recourseDao.queryRecourseFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RecourseEntity  recourseEntity = resListToDB.get(0);
			recourseEntity.setReplevyMoney(new BigDecimal(1001000));
			id = this.recourseDao.updateRecourse(recourseEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteRecourseTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<RecourseEntity> resListToDB = this.recourseDao.queryRecourseFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RecourseEntity  recourseEntity = resListToDB.get(0);
			recourseEntity.setId(null);
			id = this.recourseDao.deleteRecourse(recourseEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteRecourseByIDTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<RecourseEntity> resListToDB = this.recourseDao.queryRecourseFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RecourseEntity  recourseEntity = resListToDB.get(0);
			id = this.recourseDao.deleteRecourseByID(recourseEntity.getId());
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}
}
