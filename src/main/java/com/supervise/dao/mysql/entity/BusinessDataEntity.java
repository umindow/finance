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
@Table(name = "finance_business_data_info")
@Data
public class BusinessDataEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
    @Column(name = "org_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP,DepType.COMPREHENSIVE_DEP,DepType.RISK_DEP},modify = false,fieldCnName = "机构编号",index = 1)
    private String orgId;
    /**
     * 项目编号
     **/
    @Column(name = "proj_id")
    @DepRole(depTypes = {DepType.FINANCE_DEP,DepType.COMPREHENSIVE_DEP,DepType.RISK_DEP},modify = false,fieldCnName = "项目编号",index = 2)
    private String projId;
    
    /**
     * 客户类型：1 企业客户  2 个人客户
     */
    @Column(name = "client_type")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "客户类型",index = 3)
    private String clientType;
    
    /**
     * 客户编码
     */
    @Column(name = "client_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "客户编码",index = 4)
    private String clientId;
    
    /**
     * 客户名称
     */
    @Column(name = "client_name")
    @DepRole(depTypes = {DepType.FINANCE_DEP,DepType.COMPREHENSIVE_DEP,DepType.RISK_DEP},modify = true,fieldCnName = "客户名称",index = 5)
    private String clientName;
    
    /**
     * 证件类型：1 军官证 2 护照 3 身份证 4 其它 
     */
    @Column(name = "id_card_type")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "证件类型",index = 6)
    private String iDCardType;
    
    /**
     * 证件编码（企业填法人代表）
     */
    @Column(name = "id_card")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "证件编码",index = 7)
    private String iDCard;
    
    /**
     * 所属行业编号（一级）
     */
    @Column(name = "calling_first")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "所属行业编号(一级)",index = 8)
    private String callingFirst;
    
    /**
     * 所属行业编号（二级）
     */
    @Column(name = "calling_second")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "所属行业编号(二级)",index = 9)
    private String callingSecond;
    
    /**
     * 所属地区编号（一级）
     */
    @Column(name = "area_first")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "所属地区编号(一级)",index = 10)
    private String areaFirst;
    
    /**
     * 所属地区编号（二级）
     */
    @Column(name = "area_second")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "所属地区编号(二级)",index = 11)
    private String areaSecond;
    
    /**
     * 所属地区编号（三级）
     */
    @Column(name = "area_third")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "所属地区编号(三级)",index = 12)
    private String areaThird;
    
    /**
     * 客户规模编码：1 大型企业 2 中型企业 3 小型企业 4 微型企业
     */
    @Column(name = "company_scale")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "客户规模编码",index = 13)
    private String companyScale;

    /**
     * 是否涉农：1 是 2 否
     */
    @Column(name = "is_farming")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "是否涉农",index = 14)
    private String isFarming;

    /**
     * 业务类型
     */
    @Column(name = "business_type")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "业务类型",index = 15)
    private String businessType;
    

    
    /**
     * 合同金额
     */
    @Column(name = "contract_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "合同金额",index = 16)
    private BigDecimal contractMoney;
    
    /**
     * 已放款金额
     */
    @Column(name = "loan_money")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "已放款金额",index = 17)
    private BigDecimal loanMoney;
    
    /**
     * 贷款年利率（%），数字
     */
    @Column(name = "loan_rate")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "贷款年利率",index = 18)
    private BigDecimal loanRate;
    
    /**
     * 担保综合费率（%），数字
     */
    @Column(name = "assure_rate")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "担保综合费率",index = 19)
    private BigDecimal assureRate;
    
    /**
     * 实际放款日期
     **/
    @Column(name = "loan_date")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "实际放款日期",index = 20)
    private Date loanDate;
    
    /**
     * 合同截止日期，合同登记时的结束时间
     **/
    @Column(name = "contract_end_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "合同截止日期",index = 21)
    private Date contractEndDate;
    
    /**
     * 还款方式（指客户与银行之间的还款方式）：
     * 1、按月等额本金 
     * 2 按月等额本息 
     * 3 按季等额本金 
     * 4 按季等额本息 
     * 5 按月付息一次还本 
     * 6 到期一次性还本付息 
     * 7 不规则 
     * 8 其它
     * 
     **/
    @Column(name = "repay_type")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "还款方式",index = 22)
    private String repayType;
    
    
    /**
     * 反担保措施（如存在多项请加，分隔符）：9 抵押 4 质押 5 保证 10 信用 11 其它 
     **/
    @Column(name = "pledge_type")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "反担保措施",index = 23)
    private String pledgeType;

    /**
     * 反担保备注 
     **/
    @Column(name = "approve_option")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true,fieldCnName = "反担保备注",index = 24)
    private String approveOption;
    
    /**
     * 银行授信记录标示ID（32位UUID）
     **/
    @Column(name = "bank_credit_primary_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "银行授信记录标示ID",index = 25)
    private String bankCreditPrimaryId;
    
    /**
     * 合作银行ID（32位UUID）
     **/
    @Column(name = "co_bank_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "合作银行ID",index = 26)
    private String coBankId;
    
    /**
     * 项目状态：60 保后监管 80 项目代偿 85 项目追偿 90 项目解除
     **/
    @Column(name = "proj_status")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true,fieldCnName = "项目状态",index = 27)
    private String projSatus;

    /**
     * 担保权人
     **/
    @Column(name = "assure_person")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true,fieldCnName = "担保权人",index = 28)
    private String assurePerson;
    
    /**
     * 反担保物价值
     **/
    @Column(name = "pledge_worth")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true,fieldCnName = "反担保物价值",index = 29)
    private BigDecimal pledgeWorth;
    
    /**
     * 存单质押
     **/
    @Column(name = "is_impawn")
    @DepRole(depTypes = {DepType.RISK_DEP},modify = true,fieldCnName = "存单质押",index = 30)
    private String isImpawn;
    
    /**
     * 受理时间
     **/
    @Column(name = "accept_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "受理时间",index = 31)
    private Date acceptDate;
    
    /**
     * 合同编码
     **/
    @Column(name = "contract_id")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "合同编码",index = 32)
    private String contractId;
    
    /**
     * 客户存入保证金
     **/
    @Column(name = "client_bail_money")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "客户存入保证金",index = 33)
    private BigDecimal clientBailMoney;
    
    /**
     * 存出保证金
     **/
    @Column(name = "out_bail_money")
    @DepRole(depTypes = {DepType.FINANCE_DEP},modify = true,fieldCnName = "存出保证金",index = 34)
    private BigDecimal outBailMoney;
    
    /**
     * 资本属性:1 国有控股 2 民营控股 3 外资控股
     **/
    @Column(name = "capital_belong")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "资本属性",index = 35)
    private String capitalBelong;
    
    /**
     * 项目结束时间（实际解除时间）
     **/
    @Column(name = "proj_end_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "项目结束时间",index = 36)
    private Date projEndDate;

    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    @Column(name = "batch_date")
    @DepRole(depTypes = {DepType.COMPREHENSIVE_DEP},modify = true,fieldCnName = "批次",index = 37)
    private String batchDate;

    /**
     * 起初余额
     **/
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;


    /**
     * 首次放款时间
     **/
    @Column(name = "first_loan_date")
      private Date firstLoanDate;

    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;



    //发送状态，0:待发送 1 发送成功  -1 发送失败
    @Column(name = "sendStatus")
    private String sendStatus;

}
