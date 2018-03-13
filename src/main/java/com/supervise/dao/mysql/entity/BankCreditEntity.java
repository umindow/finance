package com.supervise.dao.mysql.entity;

import com.supervise.common.Constants;
import com.supervise.config.mysql.base.BaseEntity;
import com.supervise.config.role.DepRole;
import com.supervise.config.role.DepType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "银行授信ID",index = 1)
    private String primaryId;
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "机构编号",index = 2)
    private String orgId;
    /**
     * 银行编号
     **/
    @Column(name = "bank_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "银行编号",index = 3)
    private String bankId;
    /**
     * 主办行编码
     **/
    @Column(name = "main_bank_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "主办行编码",index = 4)
    private String mainBankId;
    /**
     * 授信类型：1 综合授信 2 单笔单议
     **/
    @Column(name = "credit_type_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信类型",index = 5)
    private String creditTypeId;
    /**
     * 授信额度
     **/
    @Column(name = "credit_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信额度",index = 6)
    private BigDecimal creditMoney;
    /**
     * 授信余额
     **/
    @Column(name = "leave_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信余额",index = 7)
    private BigDecimal leaveMoney;
    /**
     * 放大倍数
     **/
    @Column(name = "blowup_mulpitle")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "放大倍数",index = 8)
    private BigDecimal blowupMulpitle;
    /**
     * 初始保证金额
     **/
    @Column(name = "bail_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "初始保证金额",index = 9)
    private BigDecimal bailMoney;
    /**
     * 保证金比例（%）
     **/
    @Column(name = "bail_scale")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "保证金比例（%）",index = 10)
    private BigDecimal bailScale;
    /**
     * 授信开始日期
     **/
    @Column(name = "credit_start_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信开始日期",index = 11,isDate = true,dateFormat = Constants.YYYY_MM_DD)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creditStartDate;
    /**
     * 授信结束日期
     **/
    @Column(name = "credit_end_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信结束日期",index = 12,isDate = true,dateFormat = Constants.YYYY_MM_DD)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creditEndDate;
    /**
     * 单笔限额
     **/
    @Column(name = "single_money_limit")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "单笔限额",index = 13)
    private BigDecimal singleMoneyLimit;
    /**
     * 是否循环授信:1 是 2 否
     **/
    @Column(name = "is_for_credit")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "循环授信",index = 14)
    private String isForCredit;
    /**
     * 状态：1 使用 2 解除
     **/
    @Column(name = "credit_status")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "授信状态",index = 15)
    private String creditStatus;
    /**
     * 项目偏好
     **/
    @Column(name = "item_lean")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "项目偏好",index = 16)
    private String itemLean;
    /**
     * 备注
     **/
    @Column(name = "remark")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "备注",index = 17)
    private String remark;
    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "批次",index = 18)
    private String batchDate;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    //发送状态，0:待发送 1 发送成功  -1 发送失败
    @Column(name = "sendStatus")
    private String sendStatus;

}
