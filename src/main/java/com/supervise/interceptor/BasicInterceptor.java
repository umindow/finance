package com.supervise.interceptor;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xishui.hb on 2018/1/30 下午2:47.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public abstract class BasicInterceptor extends HandlerInterceptorAdapter {

    private  List<String> nonFilters = new ArrayList<String>();
    private PathMatcher pathMatcher = new AntPathMatcher();

    protected boolean isNonFilter(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(nonFilters)) {
            return true;
        }
        for (final String nonFilter : nonFilters) {
            if (pathMatcher.match(nonFilter, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    public void addNonFilter(String nonFilter) {
        this.nonFilters.add(nonFilter);
    }
}
