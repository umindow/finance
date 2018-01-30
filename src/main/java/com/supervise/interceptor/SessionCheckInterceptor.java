package com.supervise.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.supervise.common.Constants;
import com.supervise.common.HttpUtils;
import com.supervise.common.ResponseUtil;
import com.supervise.common.SessionUser;
import com.supervise.dao.mysql.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xishui.hb on 2018/1/30 下午2:59.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class SessionCheckInterceptor extends BasicInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(isNonFilter(request)){
            return true;
        }
        UserEntity userEntity = (UserEntity) request.getSession().getAttribute(Constants.SessionContant.USER_SESSION_KEY);
        if(null == userEntity){
            if(!HttpUtils.isAjax(request)&& HttpUtils.isGet(request)){
                doLogin(request, response);
            }else{//AJAX请求
                ResponseUtil.reponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Could not find a valid user session in current context!");
            }
            return false;
        }
        SessionUser.INSTANCE.setCurrentUser(userEntity);
        return true;
    }
    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (HttpUtils.isAjax(request)) {
            // ajax请求，返回302的重定向结果
            Map<String, Object> result = Maps.newHashMap();
            result.put("success", false);
            result.put("code", 302);
            result.put("message", "/index");
            ResponseUtil.reponse(response, HttpServletResponse.SC_OK, JSON.toJSONString(result));
        } else {
            // 非ajax请求，直接重定向到authUrl
            ResponseUtil.redirect(response,"/index");
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionUser.INSTANCE.removeSession();
    }
}
