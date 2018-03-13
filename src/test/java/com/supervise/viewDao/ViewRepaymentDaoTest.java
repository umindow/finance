package com.supervise.viewDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.ViewRepaymentEntity;
import com.supervise.dao.mysql.viewDao.ViewRepaymentDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ViewRepaymentDaoTest  extends BaseTest{
	
	@Autowired
	private ViewRepaymentDao repaymentDao ;


	@Test
	public void queryRepaymentFromViewTest(){
		String batchDate = "2018-02-24";
		List<ViewRepaymentEntity> resList = this.repaymentDao.queryRepaymentFromView(batchDate);
		Assert.assertEquals(3, resList.size());
	}

}
