package com.supervise.schedule;

/**
 * Created by xishui.hb on 2018/1/31 上午11:02.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface Checker {

    boolean checker(String dupKey,String scheduleName);
}
