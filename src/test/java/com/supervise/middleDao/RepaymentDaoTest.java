package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.common.Constants;
import com.supervise.common.DateUtils;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepaymentDaoTest  extends BaseTest{
	
	@Autowired
	private RepaymentDao repaymentDao ;

	
	@Test
	public void insertRepaymentToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();
		for(int i =  0; i<3;i++ ){
			RepaymentEntity entity = new RepaymentEntity();
			entity.setBatchDate(batchDate);
			entity.setContractId("1");
			entity.setInterest(new BigDecimal(1000+100*i));
			entity.setOrgId("1");
			entity.setPrincipal(new BigDecimal(1000+100*i));
			entity.setProjId("2");
			entity.setPunishMoney(new BigDecimal(1000+100*i));
			entity.setRepayDate(new Date());
			resListToDB.add(entity);
		}
		for(RepaymentEntity repaymentEntity : resListToDB ){
			int id = this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryRepaymentByConditionTest(){
		String batchDate = "2018-02-24";
		String datestr = "2018-02-06";
		String proj_id  ="1";
		String org_id = "1";
		Date repayDate = DateUtils.String2Date(datestr, Constants.YYYY_MM_DD);
		System.out.println("repayDate:"+repayDate);
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("org_id");
		queryCondition.getColumnList().add("proj_id");
		queryCondition.getColumnList().add("repay_date");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(org_id);
		queryCondition.getValueList().add(proj_id);
		queryCondition.getValueList().add(datestr);
		queryCondition.getValueList().add(batchDate);


		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();

		resListToDB = this.repaymentDao.queryRepaymentByCondition(queryCondition);

		Assert.assertEquals(6,resListToDB.size());
	}

	@Test
	public void queryRepaymentFormMiddleDBTest(){
		String batchDate = "2018-02-24";

		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();

		resListToDB = this.repaymentDao.queryRepaymentFormMiddleDB(batchDate);

		Assert.assertEquals(9,resListToDB.size());
	}

	@Test
	public void updateRepaymentTest(){
		String batchDate = "2018-02-24";
        int id  = -1;
		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();

		resListToDB = this.repaymentDao.queryRepaymentFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RepaymentEntity  repaymentEntity = resListToDB.get(0);
			repaymentEntity.setPrincipal(new BigDecimal(2000));
			id = this.repaymentDao.updateRepayment(repaymentEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteRepaymentTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();

		resListToDB = this.repaymentDao.queryRepaymentFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RepaymentEntity  repaymentEntity = resListToDB.get(0);
			repaymentEntity.setId(null);
			id = this.repaymentDao.deleteRepayment(repaymentEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}


	@Test
	public void deleteRepaymentByIDTest(){
		String batchDate = "2018-02-24";
		int id  = -1;
		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();

		resListToDB = this.repaymentDao.queryRepaymentFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			RepaymentEntity  repaymentEntity = resListToDB.get(0);
			id = this.repaymentDao.deleteRepaymentByID(repaymentEntity.getId());
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}
}
