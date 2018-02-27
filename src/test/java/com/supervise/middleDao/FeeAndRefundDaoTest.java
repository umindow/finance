package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.FeeAndRefundEntity;
import com.supervise.dao.mysql.middleDao.FeeAndRefundDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeeAndRefundDaoTest extends BaseTest{
	
	@Autowired
	private FeeAndRefundDao feeAndRefundDao ;

	
	@Test
	public void insertFeeAndRefundToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<FeeAndRefundEntity> resListToDB = new ArrayList<FeeAndRefundEntity>();
		for(int i =  0; i<5;i++ ){
			FeeAndRefundEntity entity = new FeeAndRefundEntity();
			entity.setOrgId("1");
			entity.setProjId("2");
			entity.setContractId("1");
			entity.setChargeType("232");
			entity.setChargeWay("28");
			entity.setChargeTime(new Date());
			entity.setChargeMoney(new BigDecimal(1000 + 100*i));
			entity.setBatchDate(batchDate);
			resListToDB.add(entity);
		}
		for(FeeAndRefundEntity feeAndRefundEntity : resListToDB ){
			int id = this.feeAndRefundDao.insertFeeAndRefundToMiddleDB(feeAndRefundEntity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryFeeAndRefundByConditionTest(){
		String batchDate = "2018-02-24";
		String org_id = "1";
		String proj_id = "2";
		String datestr = "2018-02-27";

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("org_id");
		queryCondition.getColumnList().add("proj_id");
		queryCondition.getColumnList().add("charge_time");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(org_id);
		queryCondition.getValueList().add(proj_id);
		queryCondition.getValueList().add(datestr);
		queryCondition.getValueList().add(batchDate);


		List<FeeAndRefundEntity> resListToDB = this.feeAndRefundDao.queryFeeAndRefundByCondition(queryCondition);

		Assert.assertEquals(5,resListToDB.size());
	}

	@Test
	public void updateFeeAndRefundTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<FeeAndRefundEntity> resListToDB =  this.feeAndRefundDao.queryFeeAndRefundFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			FeeAndRefundEntity  feeAndRefundEntity = resListToDB.get(0);
			feeAndRefundEntity.setChargeMoney(new BigDecimal(1000000));
			id = this.feeAndRefundDao.updateFeeAndRefund(feeAndRefundEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteFeeAndRefundTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<FeeAndRefundEntity> resListToDB = this.feeAndRefundDao.queryFeeAndRefundFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			FeeAndRefundEntity  feeAndRefundEntity = resListToDB.get(0);
			feeAndRefundEntity.setId(null);
			id = this.feeAndRefundDao.deleteFeeAndRefund(feeAndRefundEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteFeeAndRefundByIDTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<FeeAndRefundEntity> resListToDB = this.feeAndRefundDao.queryFeeAndRefundFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			FeeAndRefundEntity  feeAndRefundEntity = resListToDB.get(0);
			id = this.feeAndRefundDao.deleteFeeAndRefundByID(feeAndRefundEntity.getId());
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}
}
