package com.supervise.schedule.job;

import com.supervise.schedule.AbstractSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ${admin} on 2018/2/3.
 */
@Component
public class BankTrustSchedule extends AbstractSchedule {
    private final Logger logger = LoggerFactory.getLogger(BankTrustSchedule.class);
    @Override
    public void doSchedule(String dupKey) {
        logger.info("-------------------------BankTrustSchedule loaded job doing------------------");
    }

    @Override
    public String scheduleName() {
        return "bank_trust_sch";
    }
}
