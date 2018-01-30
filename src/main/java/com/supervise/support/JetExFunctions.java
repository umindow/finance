package com.supervise.support;


import com.supervise.common.SessionUser;
import com.supervise.config.role.RoleType;
import com.supervise.dao.mysql.entity.UserEntity;
import jetbrick.template.JetAnnotations;
import jetbrick.template.runtime.InterpretContext;
import jetbrick.template.web.JetWebContext;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Jetx的扩展函数
 */
@JetAnnotations.Functions
public class JetExFunctions {

    /**
     * @param text
     * @param url
     * @return
     */
    public static String link(String text, String url) {
        return "<a href=\"" + url + "\">" + text + "</a>";
    }

    /**
     * @param contextPath
     * @return
     */
    public static String url(String contextPath) {
        InterpretContext ctx = InterpretContext.current();
        String webRootPath = (String) ctx.getValueStack().getValue(JetWebContext.WEBROOT_PATH);
        return webRootPath + contextPath;
    }

    /**
     * 输出当前日期格式化 eg:${today("yyyy-MM-dd")} -> 2014-02-12
     *
     * @param format
     * @return
     */
    public static String today(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 用户菜单展示
     *
     * @return
     */
    public static List<Menu> menu() {
        MenuRepository r = SpringContextHolder.getBean(MenuRepository.class);
        return r.loadAllMenus();
    }

    public static int currentLevel() {
        return SessionUser.INSTANCE.getCurrentUser().getLevel();
    }

    public static String config(String key) {
        return "模版工程";
    }

    public static UserEntity currentUser() {
        UserEntity userEntity =  SessionUser.INSTANCE.getCurrentUser();
        return userEntity;
    }

    public static String currentUserLevelDesc() {
        RoleType[] roleTypes = RoleType.values();
        int level = currentLevel();
        for (final RoleType roleType : roleTypes) {
            if (roleType.getRoleLevel() == level) {
                return roleType.getRoleName();
            }
        }
        return "未知角色";
    }

    /**
     * 用户菜单展示
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * HTML转义
     *
     * @return
     */
    public static String escapeHtml(String str) {
        return StringEscapeUtils.escapeHtml4(str);
    }

    /**
     * Base64编码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Encode(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return Base64Utils.encodeToString(str.getBytes("UTF-8"));
    }

    /**
     * Base64解码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Decode(String str) throws UnsupportedEncodingException {
        if (isEmpty(str)) {
            return "";
        }
        return new String(Base64Utils.decodeFromString(str), "UTF-8");
    }

    /**
     * 输出日期格式化
     * <p>
     * eg:${dateToString(new Date())} ->20161123142533
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return "date is null";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 时间戳转日期
     * eg : 1481017143387 -> 2016-12-06 17:24:00
     *
     * @param timestamp
     * @return
     */
    public static String timestampToString(Long timestamp) {
        return DateFormatUtils.format(new Date(timestamp), "yyyy-MM-dd HH:mm:ss");
    }


    public static String substr(String str, int start, int end) {
        if (str == null) {
            return "[substr jet func]str is null";
        }
        if (str.length() - 1 < end) {
            return str;
        }
        return str.substring(start, end) + "...";
    }

    public static boolean listIsEmpty(List<Object> list) {
        return list.isEmpty();
    }

    public static boolean isContaionOf(List<Object> list, Object item) {
        for (Object obj : list) {
            if (obj.equals(item)) {
                return true;
            }
        }
        return false;
    }

}
