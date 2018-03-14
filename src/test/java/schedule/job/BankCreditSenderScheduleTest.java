package schedule.job;

import com.supervise.BaseTest;
import com.supervise.common.Constants;
import com.supervise.schedule.job.BankTrustSenderSchedule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BankCreditSenderScheduleTest extends BaseTest{

	@Autowired
	private BankTrustSenderSchedule bankTrustSenderSchedule;
	
	@Test
	public void doScheduleTest(){
		String dupKey = Constants.SCH_SEND_BANKCREDIT_SCHEDULE +"2018-03-14-22";
		this.bankTrustSenderSchedule.doSchedule(dupKey);
	}
}
