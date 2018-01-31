package com.supervise.core.data.spi;

/**
 * Created by xishui.hb on 2018/1/31 上午9:18.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface DataHandler<P> {

    void handler(P configParam);
}
