package com.supervise.common;

import java.util.List;

/**
 * Created by xishui.hb on 2018/3/1 下午3:21.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface Spliter<T>{

    List<List<T>> split(List<T> data,int splitSize);
}
