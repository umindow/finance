
package com.supervise.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "jgProjectServiceImpl", targetNamespace = "http://impl.webservice.why.com/", wsdlLocation = "http://10.3.4.81:9999/fios/webservice/jgProjectServiceImpl?wsdl")
public class JgProjectServiceImpl
    extends Service
{

    private final static URL JGPROJECTSERVICEIMPL_WSDL_LOCATION;
    private final static WebServiceException JGPROJECTSERVICEIMPL_EXCEPTION;
    private final static QName JGPROJECTSERVICEIMPL_QNAME = new QName("http://impl.webservice.why.com/", "jgProjectServiceImpl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.3.4.81:9999/fios/webservice/jgProjectServiceImpl?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        JGPROJECTSERVICEIMPL_WSDL_LOCATION = url;
        JGPROJECTSERVICEIMPL_EXCEPTION = e;
    }

    public JgProjectServiceImpl() {
        super(__getWsdlLocation(), JGPROJECTSERVICEIMPL_QNAME);
    }

    public JgProjectServiceImpl(WebServiceFeature... features) {
        super(__getWsdlLocation(), JGPROJECTSERVICEIMPL_QNAME, features);
    }

    public JgProjectServiceImpl(URL wsdlLocation) {
        super(wsdlLocation, JGPROJECTSERVICEIMPL_QNAME);
    }

    public JgProjectServiceImpl(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, JGPROJECTSERVICEIMPL_QNAME, features);
    }

    public JgProjectServiceImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public JgProjectServiceImpl(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Webservice
     */
    @WebEndpoint(name = "WebserviceImplPort")
    public Webservice getWebserviceImplPort() {
        return super.getPort(new QName("http://impl.webservice.why.com/", "WebserviceImplPort"), Webservice.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Webservice
     */
    @WebEndpoint(name = "WebserviceImplPort")
    public Webservice getWebserviceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.webservice.why.com/", "WebserviceImplPort"), Webservice.class, features);
    }

    private static URL __getWsdlLocation() {
        if (JGPROJECTSERVICEIMPL_EXCEPTION!= null) {
            throw JGPROJECTSERVICEIMPL_EXCEPTION;
        }
        return JGPROJECTSERVICEIMPL_WSDL_LOCATION;
    }

}
