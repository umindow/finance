package com.supervise.viewDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.ViewBusinessDataEntity;
import com.supervise.dao.mysql.viewDao.ViewBusinessDataDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ViewBusinessDataDaoTest extends BaseTest{
	
	@Autowired
	private ViewBusinessDataDao viewBusinessDataDao ;


	@Test
	public void queryRepaymentFromViewTest(){
		String batchDate = "2018-02-24";
		List<ViewBusinessDataEntity> resList = this.viewBusinessDataDao.queryBusinessDataFromView(batchDate);
		Assert.assertEquals(3, resList.size());
	}

}
