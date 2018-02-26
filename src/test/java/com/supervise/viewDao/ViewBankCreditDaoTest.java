package com.supervise.viewDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.viewDao.ViewBankCreditDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ViewBankCreditDaoTest extends BaseTest{
	
	@Autowired
	private ViewBankCreditDao viewBankCreditDao ;


	@Test
	public void queryRepaymentFromViewTest(){
		String batchDate = "2018-02-24";
		List<BankCreditEntity> resList = this.viewBankCreditDao.queryBankCreditFromView(batchDate);
		Assert.assertEquals(3, resList.size());
	}

}
