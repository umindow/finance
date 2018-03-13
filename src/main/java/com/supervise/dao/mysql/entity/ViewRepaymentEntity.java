package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/6 上午10:03.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
public class ViewRepaymentEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    private String orgId;
    /**
     * 项目编号
     **/
    private String projId;
    /**
     * 合同编号
     **/
    private String contractId;
    /**
     * 实际还款日期
     **/
    private String repayDate;
    /**
     * 实际归还本金
     **/
    private BigDecimal principal;
    /**
     * 实际归还利息
     **/
    private BigDecimal interest;
    /**
     * 收取罚息
     **/
    private BigDecimal punishMoney;


    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    private Date batchDate;

}
