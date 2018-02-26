package com.supervise.viewDao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.RepaymentEntity;
import com.supervise.dao.mysql.viewDao.ViewRepaymentDao;

public class ViewRepaymentDaoTest  extends BaseTest{
	
	@Autowired
	private ViewRepaymentDao repaymentDao ;


	@Test
	public void queryRepaymentFromViewTest(){
		String batchDate = "2018-02-24";
		List<RepaymentEntity> resList = this.repaymentDao.queryRepaymentFromView(batchDate);
		Assert.assertEquals(3, resList.size());
	}

}
