package com.supervise.mail;

import com.supervise.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceTest extends BaseTest{
	@Autowired
	private  MailService mailService;

	@Test
	public void sendEmailDataTest(){
		String dupKey = "BankCredit-Data2018-03-14-20";
		String schName ="BankCredit-Data";
		String dataType = schName;
		String batchDate= dupKey.substring(schName.length(), dupKey.length()-3);
		mailService.sendEmailData(dataType,batchDate);
	}
}
