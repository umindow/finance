
package com.supervise.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>jgBuBankCredit complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="jgBuBankCredit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bailMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bailScale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="batchDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blowupMulpitle" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="creditEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="creditMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="creditStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="creditStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isForCredit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemLean" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leaveMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="mainBankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="singleMoneyLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jgBuBankCredit", propOrder = {
    "bailMoney",
    "bailScale",
    "bankCode",
    "batchDate",
    "blowupMulpitle",
    "creditEndDate",
    "creditMoney",
    "creditStartDate",
    "creditStatus",
    "creditTypeCode",
    "id",
    "isForCredit",
    "itemLean",
    "leaveMoney",
    "mainBankCode",
    "organCode",
    "primaryId",
    "remark",
    "singleMoneyLimit"
})
public class JgBuBankCredit {

    protected BigDecimal bailMoney;
    protected BigDecimal bailScale;
    protected String bankCode;
    protected String batchDate;
    protected BigDecimal blowupMulpitle;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creditEndDate;
    protected BigDecimal creditMoney;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creditStartDate;
    protected String creditStatus;
    protected String creditTypeCode;
    protected String id;
    protected String isForCredit;
    protected String itemLean;
    protected BigDecimal leaveMoney;
    protected String mainBankCode;
    protected String organCode;
    protected String primaryId;
    protected String remark;
    protected BigDecimal singleMoneyLimit;

    /**
     * 获取bailMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBailMoney() {
        return bailMoney;
    }

    /**
     * 设置bailMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBailMoney(BigDecimal value) {
        this.bailMoney = value;
    }

    /**
     * 获取bailScale属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBailScale() {
        return bailScale;
    }

    /**
     * 设置bailScale属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBailScale(BigDecimal value) {
        this.bailScale = value;
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
     * 获取blowupMulpitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBlowupMulpitle() {
        return blowupMulpitle;
    }

    /**
     * 设置blowupMulpitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBlowupMulpitle(BigDecimal value) {
        this.blowupMulpitle = value;
    }

    /**
     * 获取creditEndDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreditEndDate() {
        return creditEndDate;
    }

    /**
     * 设置creditEndDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreditEndDate(XMLGregorianCalendar value) {
        this.creditEndDate = value;
    }

    /**
     * 获取creditMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditMoney() {
        return creditMoney;
    }

    /**
     * 设置creditMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditMoney(BigDecimal value) {
        this.creditMoney = value;
    }

    /**
     * 获取creditStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreditStartDate() {
        return creditStartDate;
    }

    /**
     * 设置creditStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreditStartDate(XMLGregorianCalendar value) {
        this.creditStartDate = value;
    }

    /**
     * 获取creditStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditStatus() {
        return creditStatus;
    }

    /**
     * 设置creditStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditStatus(String value) {
        this.creditStatus = value;
    }

    /**
     * 获取creditTypeCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditTypeCode() {
        return creditTypeCode;
    }

    /**
     * 设置creditTypeCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditTypeCode(String value) {
        this.creditTypeCode = value;
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
     * 获取isForCredit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsForCredit() {
        return isForCredit;
    }

    /**
     * 设置isForCredit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsForCredit(String value) {
        this.isForCredit = value;
    }

    /**
     * 获取itemLean属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemLean() {
        return itemLean;
    }

    /**
     * 设置itemLean属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemLean(String value) {
        this.itemLean = value;
    }

    /**
     * 获取leaveMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLeaveMoney() {
        return leaveMoney;
    }

    /**
     * 设置leaveMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLeaveMoney(BigDecimal value) {
        this.leaveMoney = value;
    }

    /**
     * 获取mainBankCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainBankCode() {
        return mainBankCode;
    }

    /**
     * 设置mainBankCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainBankCode(String value) {
        this.mainBankCode = value;
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
     * 获取primaryId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryId() {
        return primaryId;
    }

    /**
     * 设置primaryId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryId(String value) {
        this.primaryId = value;
    }

    /**
     * 获取remark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置remark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * 获取singleMoneyLimit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSingleMoneyLimit() {
        return singleMoneyLimit;
    }

    /**
     * 设置singleMoneyLimit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSingleMoneyLimit(BigDecimal value) {
        this.singleMoneyLimit = value;
    }

}
