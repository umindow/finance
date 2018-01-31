package com.supervise.core.data.spi;

/**
 * Created by xishui.hb on 2018/1/31 上午9:10.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataProcessor <T>{

    void processor(T data);
}
