package com.supervise.middleDao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.middleDao.RepaymentDao;

public class RepaymentDaoTest  extends BaseTest{
	
	@Autowired
	private RepaymentDao repaymentDao ;

	
	@Test
	public void insertRepaymentToMiddleDBTest(){
		String batchDate = "2018-02-24";
//		List<RepaymentEntity> resListFromView = this.repaymentDao.queryRepaymentFromView(batchDate);
		List<RepaymentEntity> resListToDB = new ArrayList<RepaymentEntity>();
//		for(RepaymentEntity repaymentEntity : resListFromView ){
//			RepaymentEntity entity = new RepaymentEntity();
//			entity.setBatchDate(repaymentEntity.getBatchDate());
//			entity.setContractId(entity.getContractId());
//			entity.setInterest(repaymentEntity.getInterest());
//			entity.setOrgId(repaymentEntity.getOrgId());
//			entity.setPrincipal(repaymentEntity.getPrincipal());
//			entity.setProjId(repaymentEntity.getProjId());
//			entity.setPunishMoney(repaymentEntity.getPunishMoney());
//			entity.setRepayDate(repaymentEntity.getRepayDate());
//			resListToDB.add(entity);
//		}
		for(RepaymentEntity repaymentEntity : resListToDB ){
			int id = this.repaymentDao.insertRepaymentToMiddleDB(repaymentEntity);
			Assert.assertNotNull(id);
		}
	}
}
