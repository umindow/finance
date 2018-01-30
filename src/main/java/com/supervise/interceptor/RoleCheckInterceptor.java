package com.supervise.interceptor;

import com.alibaba.fastjson.JSON;
import com.supervise.common.HttpUtils;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataRole;
import com.supervise.config.role.Role;
import com.supervise.dao.mysql.entity.UserEntity;
import com.supervise.support.Result;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午2:53.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public class RoleCheckInterceptor extends BasicInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isNonFilter(request)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Role role = handlerMethod.getMethodAnnotation(Role.class) == null ?
                handlerMethod.getBeanType().getAnnotation(Role.class) : handlerMethod.getMethodAnnotation(Role.class);
        //如果没有设置权限，那么就不用验证数据权限
        if (null == role) {
            return true;
        }
        UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
        if (null == userEntity) {
            throw new RuntimeException("用户未登录.");
        }
        if (userEntity.getLevel() >= role.roleType().getRoleLevel()) {
            //有了功能模块权限,验证数据权限
            DataRole dataRole = handlerMethod.getMethodAnnotation(DataRole.class);
            if (checkDataRole(request, response, dataRole, userEntity)) {
                return true;
            }
        }
        String message = "Resources you have visit requires a role of '" + role.roleType().getRoleName() + "' or higher!";
        pubNonRoleMessage(request, response, message);
        return false;
    }

    private boolean checkDataRole(HttpServletRequest request, HttpServletResponse response, DataRole dataRole, UserEntity userEntity) throws IOException {
        if (null == dataRole) {
            return true;
        }
        List<Integer> dataLevels = JSON.parseArray(userEntity.getDataLevels(), Integer.class);
        if (null == dataLevels) {
            return false;
        }
        return dataLevels.contains(dataRole.dataType().getDataLevel());
    }

    private void pubNonRoleMessage(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        if (HttpUtils.isAjax(request) && HttpUtils.isPost(request)) {
            Result result = Result.exception(message);
            HttpUtils.ajaxStatus(response, HttpServletResponse.SC_FORBIDDEN, JSON.toJSONString(result));
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
        }
    }
}
