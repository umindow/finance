package schedule.job;

import com.supervise.BaseTest;
import com.supervise.common.Constants;
import com.supervise.config.mysql.base.QueryCondition;
import com.supervise.config.mysql.base.QueryOperator;
import com.supervise.dao.mysql.entity.BankCreditEntity;
import com.supervise.dao.mysql.middleDao.BankCreditDao;
import com.supervise.schedule.job.BankTrustSenderSchedule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BankCreditSenderScheduleTest extends BaseTest{

	@Autowired
	private BankTrustSenderSchedule bankTrustSenderSchedule;
	@Autowired
	private BankCreditDao bankCreditDao;
	
	@Test
	public void doScheduleTest(){
		String dupKey = Constants.SCH_SEND_BANKCREDIT_SCHEDULE +"2018-03-14-22";
		this.bankTrustSenderSchedule.doSchedule(dupKey);
	}
	@Test
	public void doUpdateTest(){

		String batchDate = "2018-03-22";
		String status = Constants.DATA_SEND_SUCESS;
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.getColumnList().add("sendStatus");
		queryCondition.getColumnList().add("batch_date");

		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);
		queryCondition.getQueryOperatorList().add(QueryOperator.EQUAL);

		queryCondition.getValueList().add(Constants.DATA_READY_SEND);
		queryCondition.getValueList().add(batchDate);

		List<BankCreditEntity> reList = bankCreditDao.queryBankCreditByCondition(queryCondition);
		bankTrustSenderSchedule.setbankCreditEntitys(reList);
		bankTrustSenderSchedule.updateDataStatus(status);
	}
}
