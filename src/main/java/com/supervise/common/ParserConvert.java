package com.supervise.common;

/**
 * Created by xishui.hb on 2018/2/28 下午12:29.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface ParserConvert<T,P>{

    T covert(P param);
}
