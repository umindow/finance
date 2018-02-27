
package com.supervise.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>jgBuProjectInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="jgBuProjectInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acceptDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="approveOption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaFirst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaSecond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaThird" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assurePerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assureRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankCreditPrimaryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="batchDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callingFirst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callingSecond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capitalBelong" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientBailMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="clientCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="companyScale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="contractMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idCardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isFarming" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isImpawn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loanDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="loanMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="loanRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="organCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outBailMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="pledgeTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pledgeWorth" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="projectStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reinsureMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="repayTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jgBuProjectInfo", propOrder = {
    "acceptDate",
    "approveOption",
    "areaFirst",
    "areaSecond",
    "areaThird",
    "assurePerson",
    "assureRate",
    "bankCode",
    "bankCreditPrimaryId",
    "batchDate",
    "businessType",
    "callingFirst",
    "callingSecond",
    "capitalBelong",
    "clientBailMoney",
    "clientCode",
    "clientName",
    "clientType",
    "companyScale",
    "contractCode",
    "contractEndDate",
    "contractMoney",
    "id",
    "idCard",
    "idCardType",
    "isFarming",
    "isImpawn",
    "loanDate",
    "loanMoney",
    "loanRate",
    "organCode",
    "outBailMoney",
    "pledgeTypeCode",
    "pledgeWorth",
    "projectCode",
    "projectEndDate",
    "projectStatus",
    "reinsureMoney",
    "repayTypeCode"
})
public class JgBuProjectInfo {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar acceptDate;
    protected String approveOption;
    protected String areaFirst;
    protected String areaSecond;
    protected String areaThird;
    protected String assurePerson;
    protected BigDecimal assureRate;
    protected String bankCode;
    protected String bankCreditPrimaryId;
    protected String batchDate;
    protected String businessType;
    protected String callingFirst;
    protected String callingSecond;
    protected String capitalBelong;
    protected BigDecimal clientBailMoney;
    protected String clientCode;
    protected String clientName;
    protected String clientType;
    protected String companyScale;
    protected String contractCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    protected BigDecimal contractMoney;
    protected String id;
    protected String idCard;
    protected String idCardType;
    protected String isFarming;
    protected String isImpawn;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar loanDate;
    protected BigDecimal loanMoney;
    protected BigDecimal loanRate;
    protected String organCode;
    protected BigDecimal outBailMoney;
    protected String pledgeTypeCode;
    protected BigDecimal pledgeWorth;
    protected String projectCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar projectEndDate;
    protected String projectStatus;
    protected BigDecimal reinsureMoney;
    protected String repayTypeCode;

    /**
     * 获取acceptDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAcceptDate() {
        return acceptDate;
    }

    /**
     * 设置acceptDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAcceptDate(XMLGregorianCalendar value) {
        this.acceptDate = value;
    }

    /**
     * 获取approveOption属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveOption() {
        return approveOption;
    }

    /**
     * 设置approveOption属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveOption(String value) {
        this.approveOption = value;
    }

    /**
     * 获取areaFirst属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaFirst() {
        return areaFirst;
    }

    /**
     * 设置areaFirst属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaFirst(String value) {
        this.areaFirst = value;
    }

    /**
     * 获取areaSecond属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaSecond() {
        return areaSecond;
    }

    /**
     * 设置areaSecond属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaSecond(String value) {
        this.areaSecond = value;
    }

    /**
     * 获取areaThird属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaThird() {
        return areaThird;
    }

    /**
     * 设置areaThird属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaThird(String value) {
        this.areaThird = value;
    }

    /**
     * 获取assurePerson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssurePerson() {
        return assurePerson;
    }

    /**
     * 设置assurePerson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssurePerson(String value) {
        this.assurePerson = value;
    }

    /**
     * 获取assureRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAssureRate() {
        return assureRate;
    }

    /**
     * 设置assureRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAssureRate(BigDecimal value) {
        this.assureRate = value;
    }

    /**
     * 获取bankCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置bankCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * 获取bankCreditPrimaryId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCreditPrimaryId() {
        return bankCreditPrimaryId;
    }

    /**
     * 设置bankCreditPrimaryId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCreditPrimaryId(String value) {
        this.bankCreditPrimaryId = value;
    }

    /**
     * 获取batchDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchDate() {
        return batchDate;
    }

    /**
     * 设置batchDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchDate(String value) {
        this.batchDate = value;
    }

    /**
     * 获取businessType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * 设置businessType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessType(String value) {
        this.businessType = value;
    }

    /**
     * 获取callingFirst属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallingFirst() {
        return callingFirst;
    }

    /**
     * 设置callingFirst属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallingFirst(String value) {
        this.callingFirst = value;
    }

    /**
     * 获取callingSecond属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallingSecond() {
        return callingSecond;
    }

    /**
     * 设置callingSecond属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallingSecond(String value) {
        this.callingSecond = value;
    }

    /**
     * 获取capitalBelong属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitalBelong() {
        return capitalBelong;
    }

    /**
     * 设置capitalBelong属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitalBelong(String value) {
        this.capitalBelong = value;
    }

    /**
     * 获取clientBailMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getClientBailMoney() {
        return clientBailMoney;
    }

    /**
     * 设置clientBailMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setClientBailMoney(BigDecimal value) {
        this.clientBailMoney = value;
    }

    /**
     * 获取clientCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * 设置clientCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientCode(String value) {
        this.clientCode = value;
    }

    /**
     * 获取clientName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置clientName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientName(String value) {
        this.clientName = value;
    }

    /**
     * 获取clientType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * 设置clientType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientType(String value) {
        this.clientType = value;
    }

    /**
     * 获取companyScale属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyScale() {
        return companyScale;
    }

    /**
     * 设置companyScale属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyScale(String value) {
        this.companyScale = value;
    }

    /**
     * 获取contractCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 设置contractCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractCode(String value) {
        this.contractCode = value;
    }

    /**
     * 获取contractEndDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * 设置contractEndDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * 获取contractMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    /**
     * 设置contractMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setContractMoney(BigDecimal value) {
        this.contractMoney = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * 获取idCard属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置idCard属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCard(String value) {
        this.idCard = value;
    }

    /**
     * 获取idCardType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCardType() {
        return idCardType;
    }

    /**
     * 设置idCardType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCardType(String value) {
        this.idCardType = value;
    }

    /**
     * 获取isFarming属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFarming() {
        return isFarming;
    }

    /**
     * 设置isFarming属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFarming(String value) {
        this.isFarming = value;
    }

    /**
     * 获取isImpawn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsImpawn() {
        return isImpawn;
    }

    /**
     * 设置isImpawn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsImpawn(String value) {
        this.isImpawn = value;
    }

    /**
     * 获取loanDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoanDate() {
        return loanDate;
    }

    /**
     * 设置loanDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoanDate(XMLGregorianCalendar value) {
        this.loanDate = value;
    }

    /**
     * 获取loanMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    /**
     * 设置loanMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLoanMoney(BigDecimal value) {
        this.loanMoney = value;
    }

    /**
     * 获取loanRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLoanRate() {
        return loanRate;
    }

    /**
     * 设置loanRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLoanRate(BigDecimal value) {
        this.loanRate = value;
    }

    /**
     * 获取organCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganCode() {
        return organCode;
    }

    /**
     * 设置organCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganCode(String value) {
        this.organCode = value;
    }

    /**
     * 获取outBailMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOutBailMoney() {
        return outBailMoney;
    }

    /**
     * 设置outBailMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOutBailMoney(BigDecimal value) {
        this.outBailMoney = value;
    }

    /**
     * 获取pledgeTypeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPledgeTypeCode() {
        return pledgeTypeCode;
    }

    /**
     * 设置pledgeTypeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPledgeTypeCode(String value) {
        this.pledgeTypeCode = value;
    }

    /**
     * 获取pledgeWorth属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPledgeWorth() {
        return pledgeWorth;
    }

    /**
     * 设置pledgeWorth属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPledgeWorth(BigDecimal value) {
        this.pledgeWorth = value;
    }

    /**
     * 获取projectCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * 设置projectCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * 获取projectEndDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProjectEndDate() {
        return projectEndDate;
    }

    /**
     * 设置projectEndDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProjectEndDate(XMLGregorianCalendar value) {
        this.projectEndDate = value;
    }

    /**
     * 获取projectStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * 设置projectStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectStatus(String value) {
        this.projectStatus = value;
    }

    /**
     * 获取reinsureMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReinsureMoney() {
        return reinsureMoney;
    }

    /**
     * 设置reinsureMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReinsureMoney(BigDecimal value) {
        this.reinsureMoney = value;
    }

    /**
     * 获取repayTypeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepayTypeCode() {
        return repayTypeCode;
    }

    /**
     * 设置repayTypeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepayTypeCode(String value) {
        this.repayTypeCode = value;
    }

}
