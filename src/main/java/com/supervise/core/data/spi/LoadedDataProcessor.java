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
public interface LoadedDataProcessor<T,P> extends DataProcessor<T>{

    T loadedData(P configParam);
}
