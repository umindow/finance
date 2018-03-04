package schedule.job;

import com.supervise.BaseTest;
import com.supervise.common.Constants;
import com.supervise.schedule.job.RepaymentSenderSchedule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepaymentSenderScheduleTest extends BaseTest{

	@Autowired
	private RepaymentSenderSchedule repaymentSenderSchedule;
	
	@Test
	public void doScheduleTest(){
		String dupKey = Constants.SCH_SEND_REPAYMENT_SCHEDULE +"2018-03-04-14";
		this.repaymentSenderSchedule.doSchedule(dupKey);
	}
}
