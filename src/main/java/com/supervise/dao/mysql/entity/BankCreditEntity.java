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
@Table(name = "finance_bank_credit")
@Data
public class BankCreditEntity extends BaseEntity {
    /**
     * 银行授信记录标示ID
     **/
    @Column(name = "primary_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String primaryId;
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String orgId;
    /**
     * 银行编号
     **/
    @Column(name = "bank_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String bankId;
    /**
     * 主办行编码
     **/
    @Column(name = "main_bank_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String mainBankId;
    /**
     * 授信类型：1 综合授信 2 单笔单议
     **/
    @Column(name = "credit_type_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String creditTypeId;
    /**
     * 授信额度
     **/
    @Column(name = "credit_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal creditMoney;
    /**
     * 授信余额
     **/
    @Column(name = "leave_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal leaveMoney;
    /**
     * 放大倍数
     **/
    @Column(name = "blowup_mulpitle")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal blowupMulpitle;
    /**
     * 初始保证金额
     **/
    @Column(name = "bail_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal bailMoney;
    /**
     * 保证金比例（%）
     **/
    @Column(name = "bail_scale")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal bailScale;
    /**
     * 授信开始日期
     **/
    @Column(name = "credit_start_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private Date creditStartDate;
    /**
     * 授信结束日期
     **/
    @Column(name = "credit_end_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private Date creditEndDate;
    /**
     * 单笔限额
     **/
    @Column(name = "single_money_limit")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private BigDecimal singleMoneyLimit;
    /**
     * 是否循环授信:1 是 2 否
     **/
    @Column(name = "is_for_credit")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String isForCredit;
    /**
     * 状态：1 使用 2 解除
     **/
    @Column(name = "credit_status")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String creditStatus;
    /**
     * 项目偏好
     **/
    @Column(name = "item_lean")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String itemLean;
    /**
     * 备注
     **/
    @Column(name = "remark")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String remark;
    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true)
    private String batchDate;

}
