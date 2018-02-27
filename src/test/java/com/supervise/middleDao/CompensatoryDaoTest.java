package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.CompensatoryEntity;
import com.supervise.dao.mysql.middleDao.CompensatoryDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompensatoryDaoTest extends BaseTest{
	
	@Autowired
	private CompensatoryDao compensatoryDao ;

	
	@Test
	public void insertCompensatoryToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<CompensatoryEntity> resListToDB = new ArrayList<CompensatoryEntity>();
		for(int i =  0; i<5;i++ ){
			CompensatoryEntity entity = new CompensatoryEntity();
			entity.setOrgId("1");
			entity.setProjId("2");
			entity.setContractId("1");
			entity.setReplaceDate(new Date());
			entity.setReplaceMoney(new BigDecimal(1000 + 100 * i));
			entity.setBatchDate(batchDate);
			resListToDB.add(entity);
		}
		for(CompensatoryEntity compensatoryEntity : resListToDB ){
			int id = this.compensatoryDao.insertCompensatoryToMiddleDB(compensatoryEntity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryCompensatoryByConditionTest(){
		String batchDate = "2018-02-24";
		String org_id = "1";
		String proj_id = "2";
		String datestr = "2018-02-27";

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("org_id");
		queryCondition.getColumnList().add("proj_id");
		queryCondition.getColumnList().add("replace_date");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(org_id);
		queryCondition.getValueList().add(proj_id);
		queryCondition.getValueList().add(datestr);
		queryCondition.getValueList().add(batchDate);


		List<CompensatoryEntity> resListToDB = this.compensatoryDao.queryCompensatoryByCondition(queryCondition);

		Assert.assertEquals(4,resListToDB.size());
	}

	@Test
	public void updateCompensatoryTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<CompensatoryEntity> resListToDB =  this.compensatoryDao.queryCompensatoryFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			CompensatoryEntity  compensatoryEntity = resListToDB.get(0);
			compensatoryEntity.setReplaceMoney(new BigDecimal(2000000));
			id = this.compensatoryDao.updateCompensatory(compensatoryEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteCompensatoryTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<CompensatoryEntity> resListToDB = this.compensatoryDao.queryCompensatoryFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			CompensatoryEntity  compensatoryEntity = resListToDB.get(0);
			compensatoryEntity.setId(null);
			id = this.compensatoryDao.deleteCompensatory(compensatoryEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteCompensatoryByIDTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<CompensatoryEntity> resListToDB = this.compensatoryDao.queryCompensatoryFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			CompensatoryEntity  compensatoryEntity = resListToDB.get(0);
			id = this.compensatoryDao.deleteCompensatoryByID(compensatoryEntity.getId());
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}
}
