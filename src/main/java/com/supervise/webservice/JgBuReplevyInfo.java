
package com.supervise.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>jgBuReplevyInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="jgBuReplevyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="batchDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replevyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="replevyMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="replevyTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jgBuReplevyInfo", propOrder = {
    "batchDate",
    "contractCode",
    "id",
    "organCode",
    "projectCode",
    "replevyDate",
    "replevyMoney",
    "replevyTypeCode"
})
public class JgBuReplevyInfo {

    protected String batchDate;
    protected String contractCode;
    protected String id;
    protected String organCode;
    protected String projectCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar replevyDate;
    protected BigDecimal replevyMoney;
    protected String replevyTypeCode;

    /**
     * ��ȡbatchDate���Ե�ֵ��
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
     * ����batchDate���Ե�ֵ��
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
     * ��ȡcontractCode���Ե�ֵ��
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
     * ����contractCode���Ե�ֵ��
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
     * ��ȡid���Ե�ֵ��
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
     * ����id���Ե�ֵ��
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
     * ��ȡorganCode���Ե�ֵ��
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
     * ����organCode���Ե�ֵ��
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
     * ��ȡprojectCode���Ե�ֵ��
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
     * ����projectCode���Ե�ֵ��
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
     * ��ȡreplevyDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReplevyDate() {
        return replevyDate;
    }

    /**
     * ����replevyDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReplevyDate(XMLGregorianCalendar value) {
        this.replevyDate = value;
    }

    /**
     * ��ȡreplevyMoney���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReplevyMoney() {
        return replevyMoney;
    }

    /**
     * ����replevyMoney���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReplevyMoney(BigDecimal value) {
        this.replevyMoney = value;
    }

    /**
     * ��ȡreplevyTypeCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplevyTypeCode() {
        return replevyTypeCode;
    }

    /**
     * ����replevyTypeCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplevyTypeCode(String value) {
        this.replevyTypeCode = value;
    }

}
