package com.supervise.message;

import com.supervise.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSenderTest extends BaseTest{
	@Autowired
	private  MessageSender messageSender;

	@Test
	public void sendEmailDataTest(){
		String dupKey = "BankCredit-Data2018-03-11-20";
		String schName ="BankCredit-Data";
		String dataType = schName;
		String batchDate= dupKey.substring(schName.length(), dupKey.length()-3);
		messageSender.sendSMSData(dataType,batchDate);
	}
}
