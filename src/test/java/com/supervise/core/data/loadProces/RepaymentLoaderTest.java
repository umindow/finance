package com.supervise.core.data.loadProces;

import com.supervise.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepaymentLoaderTest extends BaseTest{

	@Autowired
	private RepaymentLoader repaymentLoader;
	
	@Test
	public void handlerTest(){
		String batchDate = "2018-02-24";
		repaymentLoader.handler(batchDate);
	}
}
