package schedule.job;

import com.supervise.BaseTest;
import com.supervise.common.Constants;
import com.supervise.schedule.job.DataLoadedSchedule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DataLoadedScheduleTest extends BaseTest{

	@Autowired
	private DataLoadedSchedule dataLoadedSchedule;
	
	@Test
	public void doScheduleTest(){
		String dupKey = Constants.SCH_DATA_LOADED_SCHEDULE +"2018-02-24-11";
		this.dataLoadedSchedule.doSchedule(dupKey);
	}
}
