package com.supervise.middleDao;

import com.supervise.BaseTest;
import com.supervise.dao.mysql.entity.BusinessDataEntity;
import com.supervise.dao.mysql.middleDao.BusinessDataDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessDataDaoTest extends BaseTest{
	
	@Autowired
	private BusinessDataDao businessDataDao ;

	
	@Test
	public void insertBusinessDataToMiddleDBTest(){
		String batchDate = "2018-02-24";
		List<BusinessDataEntity> resListToDB = new ArrayList<BusinessDataEntity>();
		for(int i =  0; i<3;i++ ){
			BusinessDataEntity entity = new BusinessDataEntity();
			entity.setAcceptDate(new Date());
			entity.setApproveOption("testoption");
			entity.setAreaFirst("1");
			entity.setAreaSecond("2");
			entity.setAreaThird("3");
			entity.setAssurePerson("me");
			entity.setAssureRate(new BigDecimal(10));
			entity.setBankCreditPrimaryId("1123124521");
			entity.setBatchDate("2018-02-23");
			entity.setBusinessType("1");
			entity.setCallingFirst("1");
			entity.setCallingSecond("2");
			entity.setCapitalBelong("1");
			entity.setClientName("she");
			entity.setClientBailMoney(new BigDecimal(100000));
			entity.setCoBankId("1231241");
			entity.setClientId("123213");
			entity.setClientType("1");
			entity.setCompanyScale("1");
			entity.setContractEndDate(new Date());
			entity.setContractId("2123");
			entity.setContractMoney(new BigDecimal(200000));
			entity.setIDCard("12321");
			entity.setIDCardType("2");
			entity.setInitialBalance(new BigDecimal(200000));
			entity.setRepayType("1");
			entity.setProjSatus("90");
			entity.setProjEndDate(new Date());
			entity.setPledgeType("1");
			entity.setOutBailMoney(new BigDecimal(1090+100*i));
			entity.setOrgId("1");
			entity.setProjId("1");
			entity.setPledgeWorth(new BigDecimal(1000+100*i));
			entity.setLoanRate(new BigDecimal(10));
			entity.setIsImpawn("1");
			entity.setIsFarming("1");
			entity.setLoanMoney(new BigDecimal(10000));
			entity.setLoanDate(new Date());
			entity.setFirstLoanDate(new Date());
			entity.setAcceptDate(new Date());
			resListToDB.add(entity);
		}
		for(BusinessDataEntity businessDataEntity : resListToDB ){
			int id = this.businessDataDao.insertBusinessDataToMiddleDB(businessDataEntity);
			Assert.assertNotNull(id);
		}
	}
}
