package com.supervise.middleDao;

import com.supervise.BaseTest;
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
}
