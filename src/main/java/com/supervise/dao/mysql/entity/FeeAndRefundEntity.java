package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
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
@Table(name = "finance_fee_and_refund_info")
@Data
public class FeeAndRefundEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false)
    private String projId;
    /**
     * 合同编号
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true)
    private String contractId;
    /**
     * 收退费标示：1 收费 2 退费
     **/
    @Column(name = "charge_way")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private String chargeWay;

    /**
     * 费用类型编码：1 担保费 2 保证金 3 其它
     **/
    @Column(name = "charge_type")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private String chargeType;

    /**
     * 实际缴费时间
     **/
    @Column(name = "charge_time")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private Date chargeTime;

    /**
     * 实际缴费金额
     **/
    @Column(name = "charge_money")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private BigDecimal chargeMoney;

    
    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private String batchDate;

}