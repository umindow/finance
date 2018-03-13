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
public class ViewBusinessDataEntity extends BaseEntity {
    
    /**
     * 机构编号（金融办分配的公司编号）,默认为：渝061001L
     **/
   private String orgId;
    /**
     * 项目编号
     **/
    private String projId;
    
    /**
     * 客户类型：1 企业客户  2 个人客户
     */
    private String clientType;
    
    /**
     * 客户编码
     */
    private String clientId;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    /**
     * 证件类型：1 军官证 2 护照 3 身份证 4 其它 
     */
     private String iDCardType;
    
    /**
     * 证件编码（企业填法人代表）
     */
     private String iDCard;
    
    /**
     * 所属行业编号（一级）
     */
    private String callingFirst;
    
    /**
     * 所属行业编号（二级）
     */
    private String callingSecond;
    
    /**
     * 所属地区编号（一级）
     */
     private String areaFirst;
    
    /**
     * 所属地区编号（二级）
     */
    private String areaSecond;
    
    /**
     * 所属地区编号（三级）
     */
    private String areaThird;
    
    /**
     * 客户规模编码：1 大型企业 2 中型企业 3 小型企业 4 微型企业
     */
    private String companyScale;

    /**
     * 是否涉农：1 是 2 否
     */
    private String isFarming;

    /**
     * 业务类型
     */
    private String businessType;
    

    
    /**
     * 合同金额
     */
    private BigDecimal contractMoney;
    
    /**
     * 已放款金额
     */
    private BigDecimal loanMoney;
    
    /**
     * 贷款年利率（%），数字
     */
    private BigDecimal loanRate;
    
    /**
     * 担保综合费率（%），数字
     */
    private BigDecimal assureRate;
    
    /**
     * 实际放款日期
     **/
    private String loanDate;//需要转换
    
    /**
     * 合同截止日期，合同登记时的结束时间
     **/
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
    private String repayType;
    
    
    /**
     * 反担保措施（如存在多项请加，分隔符）：9 抵押 4 质押 5 保证 10 信用 11 其它 
     **/
    private String pledgeType;

    /**
     * 反担保备注 
     **/
    private String approveOption;
    
    /**
     * 银行授信记录标示ID（32位UUID）
     **/
    private String bankCreditPrimaryId;
    
    /**
     * 合作银行ID（32位UUID）
     **/
    private String coBankId;
    
    /**
     * 项目状态：60 保后监管 80 项目代偿 85 项目追偿 90 项目解除
     **/
     private String projSatus;

    /**
     * 担保权人
     **/
    private String assurePerson;
    
    /**
     * 反担保物价值
     **/
    private BigDecimal pledgeWorth;
    
    /**
     * 存单质押
     **/
    private String isImpawn;
    
    /**
     * 受理时间
     **/
    private Date acceptDate;
    
    /**
     * 合同编码
     **/
    private String contractId;
    
    /**
     * 客户存入保证金
     **/
    private BigDecimal clientBailMoney;
    
    /**
     * 存出保证金
     **/
    private BigDecimal outBailMoney;
    
    /**
     * 资本属性:1 国有控股 2 民营控股 3 外资控股
     **/
    private String capitalBelong;
    
    /**
     * 项目结束时间（实际解除时间）
     **/
    private Date projEndDate;

    /**
     * 批次（当前传输日期，格式yyyyMMdd）
     **/
    private Date batchDate;

    /**
     * 起初余额
     **/
    private BigDecimal initialBalance;


    /**
     * 首次放款时间
     **/
    private Date firstLoanDate;

}
