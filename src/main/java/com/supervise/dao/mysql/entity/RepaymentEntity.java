package com.supervise.dao.mysql.entity;

import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "finance_repayment_info")
@Data
public class RepaymentEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false,fieldCnName = "机构编号",index = 1)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false,fieldCnName = "项目编号",index = 2)
    private String projId;
    /**
     * 合同编号
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "合同编号",index = 3)
    private String contractId;
    /**
     * 实际还款日期
     **/
    @Column(name = "repay_date")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "实际还款日期",index = 4,isDate = true,dateFormat = Constants.YYYY_MM_DD)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date repayDate;
    /**
     * 实际归还本金
     **/
    @Column(name = "principal")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "实际归还本金",index = 5)
    private BigDecimal principal;
    /**
     * 实际归还利息
     **/
    @Column(name = "interest")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "实际归还利息",index = 6)
    private BigDecimal interest;
    /**
     * 收取罚息
     **/
    @Column(name = "punish_money")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "收取罚息",index = 7)
    private BigDecimal punishMoney;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "批次",index = 8)
    private String batchDate;

    //发送状态，0:待发送 1 发送成功  -1 发送失败
    @Column(name = "sendStatus")
    private String sendStatus;

    /**
     * 客户编码
     */
    @Transient
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false,fieldCnName = "客户编码",index = 9)
    private String clientId;

    /**
     * 客户名称
     */
    @Transient
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = false,fieldCnName = "客户名称",index = 10)
    private String clientName;
}
