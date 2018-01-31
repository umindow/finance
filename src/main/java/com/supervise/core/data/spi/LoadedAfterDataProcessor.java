package com.supervise.core.data.spi;

/**
 * Created by xishui.hb on 2018/1/31 上午9:12.
 *
 * @author xishui
 * Description:
 * Modify Record
 * ----------------------------------------
 * User    |    Time    |    Note
 */
public interface LoadedAfterDataProcessor<T,P> extends LoadedDataProcessor<T,P>{

    void afterData(T dataParam);
}
