package com.supervise.core.data.loadProces;

import com.supervise.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BusinessDataLoaderTest extends BaseTest{

	@Autowired
	private BusinessDataLoader businessDataLoader;
	
	@Test
	public void handlerTest(){
		String batchDate = "2018-02-24";
		businessDataLoader.handler(batchDate);
	}
}
