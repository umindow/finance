package com.supervise.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xishui.hb on 2018/2/12 下午4:23.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public final class DateUtils {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Date parseStringDate(String date,String pattern){
        String pt = (null == pattern) ? DEFAULT_PATTERN:pattern;
        SimpleDateFormat format = new SimpleDateFormat(pt);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
