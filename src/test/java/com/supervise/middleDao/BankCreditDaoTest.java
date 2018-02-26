package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
