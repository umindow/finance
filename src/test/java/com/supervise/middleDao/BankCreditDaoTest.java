package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankCreditDaoTest extends BaseTest{
	
	@Autowired
	private BankCreditDao bankCreditDao ;

	
	@Test
	public void insertBankCreditToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<BankCreditEntity> resListToDB = new ArrayList<BankCreditEntity>();
		for(int i =  0; i<3;i++ ){
			BankCreditEntity entity = new BankCreditEntity();
//			entity.setBatchDate(batchDate);
//			entity.setContractId("1");
//			entity.setInterest(new BigDecimal(1000+100*i));
//			entity.setOrgId("1");
//			entity.setPrincipal(new BigDecimal(1000+100*i));
//			entity.setProjId("2");
//			entity.setPunishMoney(new BigDecimal(1000+100*i));
//			entity.setRepayDate(new Date());
			resListToDB.add(entity);
		}
		for(BankCreditEntity bankCreditEntity : resListToDB ){
			int id = this.bankCreditDao.insertBankCreditToMiddleDB(bankCreditEntity);
			Assert.assertNotNull(id);
		}
	}

	@Test
	public void queryBankCreditByConditionTest(){
		String batchDate = "2018-02-11";
		String org_id = "渝061001L";
		String primary_id = "A00001";

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("org_id");
		queryCondition.getColumnList().add("primary_id");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(org_id);
		queryCondition.getValueList().add(primary_id);
		queryCondition.getValueList().add(batchDate);


		List<BankCreditEntity> resListToDB = this.bankCreditDao.queryBankCreditByCondition(queryCondition);

		Assert.assertEquals(1,resListToDB.size());
	}

	@Test
	public void updateRepaymentTest(){
		String batchDate = "2018-02-11";
		int id  = -1;
		List<BankCreditEntity> resListToDB =  this.bankCreditDao.queryBankCreditFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			BankCreditEntity  bankCreditEntity = resListToDB.get(0);
			bankCreditEntity.setCreditMoney(new BigDecimal(1000000));
			id = this.bankCreditDao.updateBankCredit(bankCreditEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteRepaymentTest(){
		String batchDate = "2018-02-11";
		int id  = -1;
		List<BankCreditEntity> resListToDB = this.bankCreditDao.queryBankCreditFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			BankCreditEntity  bankCreditEntity = resListToDB.get(0);
			bankCreditEntity.setId(null);
			id = this.bankCreditDao.deleteRepayment(bankCreditEntity);
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}

	@Test
	public void deleteRepaymentByIDTest(){
		String batchDate = "2018-02-11";
		int id  = -1;
		List<BankCreditEntity> resListToDB = this.bankCreditDao.queryBankCreditFormMiddleDB(batchDate);
		if(resListToDB.size()>0){
			BankCreditEntity  bankCreditEntity = resListToDB.get(0);
			id = this.bankCreditDao.deleteRepaymentByID(bankCreditEntity.getId());
			System.out.println("id:"+id);
			Assert.assertNotEquals(-1,id);
		}
	}
}
