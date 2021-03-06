
package com.supervise.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Webservice", targetNamespace = "http://webservice.why.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Webservice {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuRepayDetailAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuRepayDetailAry")
    @ResponseWrapper(localName = "saveJgBuRepayDetailAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuRepayDetailAryResponse")
    public Long saveJgBuRepayDetailAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuRepayDetail> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuProjectInfoAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuProjectInfoAry")
    @ResponseWrapper(localName = "saveJgBuProjectInfoAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuProjectInfoAryResponse")
    public Long saveJgBuProjectInfoAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuProjectInfo> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuChargeRecordAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuChargeRecordAry")
    @ResponseWrapper(localName = "saveJgBuChargeRecordAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuChargeRecordAryResponse")
    public Long saveJgBuChargeRecordAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuChargeRecord> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuReplaceInfoAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuReplaceInfoAry")
    @ResponseWrapper(localName = "saveJgBuReplaceInfoAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuReplaceInfoAryResponse")
    public Long saveJgBuReplaceInfoAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuReplaceInfo> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuBankCreditInfoAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuBankCreditInfoAry")
    @ResponseWrapper(localName = "saveJgBuBankCreditInfoAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuBankCreditInfoAryResponse")
    public Long saveJgBuBankCreditInfoAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuBankCredit> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveJgBuReplevyInfoAry", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuReplevyInfoAry")
    @ResponseWrapper(localName = "saveJgBuReplevyInfoAryResponse", targetNamespace = "http://webservice.why.com/", className = "com.supervise.webservice.SaveJgBuReplevyInfoAryResponse")
    public Long saveJgBuReplevyInfoAry(
            @WebParam(name = "arg0", targetNamespace = "")
                    List<JgBuReplevyInfo> arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    Integer arg1)
        throws Exception_Exception
    ;

}
