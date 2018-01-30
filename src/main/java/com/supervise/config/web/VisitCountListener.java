package com.supervise.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 */
public class VisitCountListener implements ServletRequestListener {

    private Logger logger = LoggerFactory.getLogger(VisitCountListener.class);

    private ApplicationContext applicationContext = null;

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        HttpSession session = request.getSession();
        ServletContext servletContext = session.getServletContext();
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        try {
            logger.info(request.getRequestURI());
            // record user operator message
        }catch (Exception e){
            logger.error("count the visit error:" + e.getMessage());
        }
    }
}
