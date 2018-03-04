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
@Table(name = "finance_compensatory_info")
@Data
public class CompensatoryEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = false)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = false)
    private String projId;
    /**
     * 合同编号
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private String contractId;
    /**
     * 代偿日期
     **/
    @Column(name = "replace_date")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private Date replaceDate;
    /**
     * 代偿金额
     **/
    @Column(name = "replace_money")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private BigDecimal replaceMoney;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true)
    private String batchDate;

    //发送状态，0:待发送 1 发送成功  -1 发送失败
    @Column(name = "sendStatus")
    private String sendStatus;
}
