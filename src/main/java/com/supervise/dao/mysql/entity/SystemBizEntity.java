package com.supervise.dao.mysql.entity;

import com.supervise.config.mysql.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/1/31 上午10:06.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
@Data
@Table(name = "finance_business_data")
public class SystemBizEntity extends BaseEntity{
    /** 机构编码**/
    @Column(name = "org_id")
    private String orgId;
    /** 项目编码**/
    @Column(name = "proj_id")
    private String projId;
    /** 客户类型：1 企业客户  2 个人客户**/
    @Column(name = "client_type")
    private String clientType;
    /**客户编号 **/
    @Column(name = "client_id")
    private String clientId;
    /** 客户名称**/
    @Column(name = "client_name")
    private String clientName;
    /** 证件类型：1 军官证 2 护照 3 身份证 4 其它**/
    @Column(name = "id_card_type")
    private String idCardType;
    /** 证件编码（企业填法人代表）**/
    @Column(name = "id_card")
    private String idCard;
    /** 所属行业编号（一级）**/
    @Column(name = "calling_first")
    private String callingFirst;
    /** 所属行业编号（二级）**/
    @Column(name = "calling_second")
    private String callingSecond;
    /** 所属地区编号（一级）**/
    @Column(name = "area_first")
    private String areaFirst;
    /** 所属地区编号（二级）**/
    @Column(name = "area_second")
    private String areaSecond;
    /** 所属地区编号（三级）**/
    @Column(name = "area_third")
    private String areaThird;
    /** 客户规模编码：1 大型企业 2 中型企业 3 小型企业 4 微型企业**/
    @Column(name = "company_scale")
    private String companyScale;
    /** 是否涉农：1 是 2 否**/
    @Column(name = "is_farming")
    private String isFarming;
    /*8
     `contract_money` decimal(14,2) NOT NULL COMMENT '合同金额',
  `loan_money` decimal(14,2) NOT NULL COMMENT '已放款金额',
  `loan_rate` decimal(14,2) NOT NULL COMMENT '贷款年利率（%），传入数字',
  `assure_rate` decimal(14,2) NOT NULL COMMENT '担保综合费率（%），传入数字',
  `loan_date` date NOT NULL COMMENT '放款日期,实际放款日期',
  `contract_end_date` date NOT NULL COMMENT '合同截止日期，合同登记时的结束时间',
  `repay_type_id` varchar(10) NOT NULL COMMENT '还款方式（指客户与银行之间的还款方式）：1、按月等额本金 2 按月等额本息 3 按季等额本金 4 按季等额本息 5 按月付息一次还本 6 到期一次性还本付息 7 不规则 8 其它',
  `pledge_type_id` varchar(10) NOT NULL COMMENT '反担保措施（如存在多项请加，分隔符）：9 抵押 4 质押 5 保证 10 信用 11 其它 ',
  `approve_option` varchar(900) DEFAULT NULL COMMENT '反担保备注',
  `bank_credit_primary_id` varchar(40) DEFAULT NULL COMMENT '银行授信记录标示ID（32位UUID）',
  `co_bank_id` varchar(40) DEFAULT NULL COMMENT '合作银行ID（32位UUID）',
  `proj_status` varchar(40) NOT NULL COMMENT '项目状态：60 保后监管 80 项目代偿 85 项目追偿 90 项目解除',
  `assure_person` varchar(900) DEFAULT NULL COMMENT '担保权人',
  `pledge_worth` decimal(14,2) DEFAULT NULL COMMENT '反担保物价值',
     */
    /****/
    @Column(name = "")
    private BigDecimal contractMoney;
    private BigDecimal loanMoney;
    private BigDecimal loanRate;
    private BigDecimal assureRate;
    private Date loanDate;
    private Date contractEndDate;
    private String repayTypeId;
    private String pledgeTypeId;
    private String approveOption;
    private String bankCreditPrimaryId;
    private String coBankId;
    private String projStatus;
    private String assurePerson;
    private String pledgeWorth;
}
