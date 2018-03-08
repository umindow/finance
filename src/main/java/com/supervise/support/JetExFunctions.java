package com.supervise.support;


import com.alibaba.fastjson.JSON;
import com.supervise.cache.FiedRoleCache;
import com.supervise.common.SessionUser;
import com.supervise.config.role.DataType;
import com.supervise.config.role.DepType;
import com.supervise.config.role.RoleType;
import com.supervise.dao.mysql.entity.UserEntity;
import jetbrick.template.JetAnnotations;
import jetbrick.template.runtime.InterpretContext;
import jetbrick.template.web.JetWebContext;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        List<Menu> menus = r.loadAllMenus();
        List<Menu> resMenus = new ArrayList<Menu>();
        if (CollectionUtils.isEmpty(menus)) {
            return null;
        }
        UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
        DepType depType = DepType.depType(Integer.valueOf(userEntity.getDepId()));
        DataType[] dataTypes = depType.getDataTypes();
        if(null ==dataTypes || dataTypes.length <= 0 || userEntity.getLevel() == RoleType.MANAGER.getRoleLevel()){
            return menus;
        }
        for (final Menu menu : menus) {
            if (!menu.isCheckDep() || CollectionUtils.isEmpty(menu.getSub_menus())) {
                resMenus.add(menu);
                continue;
            }
            List<Menu> subMenus = new ArrayList<Menu>();
            for (Menu subMenu : menu.getSub_menus()) {
                if (!subMenu.isCheckDep()) {
                    subMenus.add(subMenu);
                    continue;
                }
                for(final DataType dataType : dataTypes){
                    if(dataType.getDataName().equals(subMenu.getName())){
                        subMenus.add(subMenu);
                        break;
                    }
                }
            }
            menu.setSub_menus(subMenus);
            resMenus.add(menu);
        }
        return resMenus;
    }

    public static int currentLevel() {
        return SessionUser.INSTANCE.getCurrentUser().getLevel();
    }

    public static String config(String key) {
        return "模版工程";
    }

    public static UserEntity currentUser() {
        UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
        return userEntity;
    }

    public static String userLevelDesc(int level) {
        RoleType[] roleTypes = RoleType.values();
        for (final RoleType roleType : roleTypes) {
            if (roleType.getRoleLevel() == level) {
                return roleType.getRoleName();
            }
        }
        return "未知角色";
    }

    public static String userDepDesc(String depId) {
        return DepType.depDesc(Integer.valueOf(depId));
    }

    public static String userDataRole(String dataRoleJson) {
        DataType[] dataTypes = DataType.values();
        List<Integer> dataRoles = JSON.parseArray(dataRoleJson, Integer.class);
        StringBuilder builder = new StringBuilder();
        for (final DataType dataType : dataTypes) {
            if (dataRoles.contains(dataType.getDataLevel())) {
                builder.append(dataType.getDataName()).append(",");
            }
        }
        return "".equals(builder.toString()) ? "未知" : builder.toString().substring(0, builder.toString().length() - 1);
    }

    /**
     * 根据数据类型和字段，判断当前用户是否有修改权限(页面)
     *
     * @param dataType
     * @param field
     * @return
     */
    public static boolean isFieldModify(int dataType, String field) {
        FiedRoleCache.DepRoleRef depRoleRef = FiedRoleCache.depRoleRef(dataType, field);
        if (null == depRoleRef || null == depRoleRef.getDepTypes() || depRoleRef.getDepTypes().length <= 0) {
            return true;
        }
        if (!depRoleRef.isModify()) {
            return false;
        }
        UserEntity userEntity = SessionUser.INSTANCE.getCurrentUser();
        if (null == userEntity) {
            return false;
        }
        for (final DepType depType : depRoleRef.getDepTypes()) {
            if (depType.getDepId() == Integer.valueOf(userEntity.getDepId())) {
                return true;
            }
        }
        return false;
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
